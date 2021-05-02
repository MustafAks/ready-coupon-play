package com.bilyoner.assignment.couponapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CouponApiException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public CouponApiException(ErrorCodeEnum errorCode) {
        super();
        this.errorCode = errorCode;
    }

}
