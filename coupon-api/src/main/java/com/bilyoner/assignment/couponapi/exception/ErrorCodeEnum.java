package com.bilyoner.assignment.couponapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_FOUND_ERROR(1002, "Content not found.", HttpStatus.BAD_REQUEST),
    MBS_ERROR(1003,"MBS error", HttpStatus.BAD_REQUEST),
    EXPIRED_EVENT_DATE(1004,"Event date expired", HttpStatus.BAD_REQUEST),
    COMPATIBLE_WITH_EACH_OTHER_BET(1005,"Bets are not incompatible with each other (FootBall & Tennis) ", HttpStatus.BAD_REQUEST),
    EVENT_NOT_FOUND(1006,"Event not found", HttpStatus.BAD_REQUEST),
    COUPON_ALREADY_PURCHASED(1007,"Coupon Already Purchased", HttpStatus.BAD_REQUEST),
    COUPON_NOT_FOUND(1008,"Coupon not  found", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
