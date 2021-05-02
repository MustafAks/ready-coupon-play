package com.bilyoner.assignment.balanceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BalanceApiException.class)
    public final ResponseEntity<BalanceApiException> handleBalanceApiException(BalanceApiException ex,
                                                                               WebRequest request) {
        return prepareResponse(ex);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(
            Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<BalanceApiException> prepareResponse(BalanceApiException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(exception);
    }

}
