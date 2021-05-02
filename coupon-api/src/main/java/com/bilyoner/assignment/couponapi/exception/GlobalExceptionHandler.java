package com.bilyoner.assignment.couponapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CouponApiException.class)
    public final ResponseEntity<CouponApiException> handleCouponApiException(CouponApiException ex,
                                                                             WebRequest request) {
        return prepareResponse(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Exception> handleException(
            Exception ex) {
        return ResponseEntity.status(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus()).body(ex);
    }

    private static ResponseEntity<CouponApiException> prepareResponse(CouponApiException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(exception);
    }
}