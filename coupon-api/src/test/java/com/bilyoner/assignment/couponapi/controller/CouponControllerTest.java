package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.CouponApiApplication;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.service.CouponService;
import com.bilyoner.assignment.couponapi.util.PrepareClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CouponApiApplication.class)
public class CouponControllerTest extends PrepareClass {

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CouponService couponService;

    @Mock
    ModelMapper mapper;


    @Test
    public void create_coupon_shouldBeSuccess() {
        CouponCreateRequest request = createCouponRequest();
        CouponDTO couponDTO = createCouponDTO();
        EventEntity eventEntity = createEventEntity();
        when(mapper.map(eventEntity, CouponDTO.class)).thenReturn(couponDTO);
        doReturn(couponDTO).when(couponService).createCoupon(request);
        ResponseEntity<CouponDTO> response = couponController.createCoupon(request);
        assert response != null;
    }
}
