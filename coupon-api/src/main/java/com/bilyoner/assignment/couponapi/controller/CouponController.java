package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/coupons")
public class CouponController {

    private final CouponService couponService;


    @GetMapping
    public ResponseEntity<Page<CouponDTO>> getAllCouponsByCouponStatus(@RequestParam CouponStatusEnum couponStatus,
                                                                       @RequestParam int pageIndex,
                                                                       @RequestParam int pageSize) {
        return new ResponseEntity(couponService.getAllCouponsByCouponStatus(couponStatus,pageIndex,pageSize), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody @Valid CouponCreateRequest couponCreateRequest) {
        return new ResponseEntity(couponService.createCoupon(couponCreateRequest), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<CouponDTO>> getPlayedCoupons(@PathVariable Long userId) {
        return new ResponseEntity(couponService.getPlayedCoupons(userId), HttpStatus.OK);
    }

    @PostMapping("play")
    public ResponseEntity<List<CouponDTO>> playCoupons(@Valid @RequestBody CouponPlayRequest couponPlayRequest) {
        return new ResponseEntity(couponService.playCoupons(couponPlayRequest), HttpStatus.OK);
    }

    @PutMapping("{couponId}")
    public ResponseEntity<CouponDTO> cancelCoupon(@PathVariable Long couponId) {
        return new ResponseEntity(couponService.cancelCoupon(couponId), HttpStatus.OK);
    }
}