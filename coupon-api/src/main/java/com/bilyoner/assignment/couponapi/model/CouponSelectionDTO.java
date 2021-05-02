package com.bilyoner.assignment.couponapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponSelectionDTO {

    private Long id;
    private CouponDTO coupon;
    private EventDTO event;
    private LocalDateTime createDate;

}
