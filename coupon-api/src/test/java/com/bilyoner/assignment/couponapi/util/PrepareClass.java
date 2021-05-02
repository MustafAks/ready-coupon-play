package com.bilyoner.assignment.couponapi.util;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.*;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;


public class PrepareClass {

    private static final long ID = 1L;
    private static final int MBS = 1;
    private static final String EVENT_NAME = "Ankaragücü-Beşiktaş";
    private static final int DAYS = 30;
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final LocalDateTime EVENT_DATE = LocalDateTime.now().plus(Duration.ofDays(DAYS));
    private static final BigDecimal COST = BigDecimal.valueOf(5);


    public CouponCreateRequest createCouponRequest() {
        return CouponCreateRequest.builder()
                .eventIds(Collections.singletonList(ID))
                .build();
    }

    public EventEntity createEventEntity() {
        return EventEntity.builder()
                .mbs(MBS)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(EVENT_DATE)
                .name(EVENT_NAME)
                .id(ID)
                .build();
    }

    public EventCreateRequest eventCreateRequest() {
        return EventCreateRequest.builder()
                .mbs(MBS)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(EVENT_DATE)
                .name(EVENT_NAME)
                .build();
    }

    public CouponEntity createCouponEntity() {
        return CouponEntity.builder()
                .id(ID)
                .cost(COST)
                .status(CouponStatusEnum.CREATED)
                .createDate(DATE)
                .updateDate(DATE)
                .build();
    }

    public CouponPlayRequest createCouponPlayRequest() {
        return CouponPlayRequest.builder()
                .couponIds(Arrays.asList(1l))
                .userId(1l)
                .build();
    }

    public EventDTO createEventDTO() {
        return EventDTO.builder()
                .id(ID)
                .type(EventTypeEnum.FOOTBALL)
                .name(EVENT_NAME)
                .mbs(MBS)
                .eventDate(EVENT_DATE)
                .build();
    }


    public CouponSelectionEntity createCouponSelectionEntity(EventEntity eventEntity, CouponEntity couponEntity) {
        return CouponSelectionEntity.builder()
                .event(eventEntity)
                .coupon(couponEntity)
                .createDate(DATE)
                .id(ID)
                .build();
    }

    public CouponDTO createCouponDTO() {
        return CouponDTO.builder()
                .id(ID)
                .cost(COST)
                .status(CouponStatusEnum.CREATED)
                .createDate(DATE)
                .updateDate(DATE)
                .build();
    }


}
