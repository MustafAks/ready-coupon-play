package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.ServiceBase;
import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.repository.CouponSelectionRepository;
import com.bilyoner.assignment.couponapi.util.PrepareClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceBase
public class CouponServiceTest extends PrepareClass {

    @InjectMocks
    CouponService couponService;
    @Mock
    CouponRepository couponRepository;
    @Mock
    CouponSelectionRepository couponSelectionRepository;
    @Mock
    EventService eventService;
    @Mock
    BalanceService balanceService;
    @Mock
    ModelMapper mapper;


    @Test
    public void createCoupon_Test_should_success() {
        CouponCreateRequest request = createCouponRequest();
        List<Long> eventIds = request.getEventIds();

        EventEntity eventEntity = createEventEntity();
        List<EventEntity> events = Collections.singletonList(eventEntity);
        when(eventService.getEventsByIds(eventIds)).thenReturn(events);
        CouponEntity couponEntity = createCouponEntity();
        when(couponRepository.save(any(CouponEntity.class))).thenReturn(couponEntity);

        CouponSelectionEntity couponSelectionEntity = createCouponSelectionEntity(eventEntity, couponEntity);
        when(couponSelectionRepository.save(any(CouponSelectionEntity.class))).thenReturn(couponSelectionEntity);

        CouponDTO couponDTO = createCouponDTO();
        when(mapper.map(couponEntity, CouponDTO.class)).thenReturn(couponDTO);
        CouponDTO response = couponService.createCoupon(request);

        assert response != null;
        assert response.getStatus() == CouponStatusEnum.CREATED;

    }


    @Test
    public void createCoupon_Test_should_Error_Event_Not_Found() {
        assertThrows(CouponApiException.class,
                () -> {
                    CouponCreateRequest request = createCouponRequest();
                    couponService.createCoupon(request);
                });
    }

    @Test
    public void cancelCoupon_Test_should_Error_Not_Found() {
        assertThrows(CouponApiException.class,
                () -> {
                    couponService.cancelCoupon(1l);
                });
    }

    @Test
    public void cancelCoupon_Test_should_success() {
        CouponEntity couponEntity = createCouponEntity();
        couponEntity.setPlayDate(LocalDateTime.now().plus(Duration.ofDays(30)));
        when(couponRepository.findByIdAndStatus(couponEntity.getId(), CouponStatusEnum.PLAYED)).thenReturn(java.util.Optional.ofNullable(couponEntity));
        couponService.cancelCoupon(couponEntity.getId());
    }

    @Test
    public void checkCouponPlayDate_Test_should_Error() {
        assertThrows(CouponApiException.class,
                () -> {
                    CouponEntity couponEntity = createCouponEntity();
                    couponEntity.setPlayDate(LocalDateTime.now().minus(Duration.ofDays(30)));
                    when(couponRepository.findByIdAndStatus(couponEntity.getId(), CouponStatusEnum.PLAYED)).thenReturn(java.util.Optional.ofNullable(couponEntity));
                    couponService.cancelCoupon(couponEntity.getId());
                });
    }


    @Test
    public void playCoupons_Test_should_success() {
        CouponPlayRequest couponPlayRequest = createCouponPlayRequest();
        CouponEntity couponEntity = createCouponEntity();
        couponEntity.setPlayDate(LocalDateTime.now().plus(Duration.ofDays(30)));
        when(couponRepository.findByIdIn(couponPlayRequest.getCouponIds())).thenReturn(Collections.singletonList(couponEntity));
        List<CouponDTO> response = couponService.playCoupons(couponPlayRequest);
        assert  response != null;
    }


    @Test
    public void playCoupons_Test_should_error_coupon_not_found() {
        assertThrows(CouponApiException.class,
                () -> {
                    CouponPlayRequest couponPlayRequest = createCouponPlayRequest();
                    couponService.playCoupons(couponPlayRequest);
                });
    }

}
