package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus) {
        /**
         * TODO : Implement get coupons logic
         */
        return null;
    }

    public CouponDTO createCoupon(CouponCreateRequest couponCreateRequest) {

        /**
         * TODO : Implement create coupon logic
         */
        return null;
    }

    public List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest) {

        /**
         * TODO : Implement play coupons
         */
        return null;
    }

    public CouponDTO cancelCoupon(Long couponId) {
        /**
         * TODO : Implement cancel coupon
         */
        return null;
    }

    public List<CouponDTO> getPlayedCoupons(Long userId) {
        /**
         * TODO : Implement get played coupons
         */
        return null;
    }
}
