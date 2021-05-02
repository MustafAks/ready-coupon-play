package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.repository.CouponSelectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final EventService eventService;
    private final CouponSelectionRepository couponSelectionRepository;
    private final BalanceService balanceService;
    private final ModelMapper mapper;

    public Page<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus, int pageIndex, int pageSize) {
        log.info("******** Incoming User couponStatus Start ********");
        log.info("getAllCouponsByCouponStatus {} {} {}", kv("couponStatus", couponStatus),
                kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<CouponDTO> response = couponRepository.findByStatus(couponStatus, PageRequest.of(pageIndex, pageSize))
                .map(entity -> {
                    CouponDTO dto = mapper.map(entity, CouponDTO.class);
                    dto.setEventIds(new ArrayList<>());
                    entity.getSelections().forEach(s -> dto.getEventIds().add(s.getEvent().getId()));
                    return dto;
                });
        log.info("getAllCouponsByCouponStatus {}", kv("response", response));
        log.info("******** Incoming User couponStatus End ********");
        return response;
    }

    public CouponDTO createCoupon(CouponCreateRequest couponCreateRequest) {
        log.info("******** Incoming User CouponCreateRequest Start ********");
        log.info("createCoupon-start {}", kv("couponCreateRequest", couponCreateRequest));
        List<Long> eventIds = couponCreateRequest.getEventIds();
        List<EventEntity> events = eventService.getEventsByIds(eventIds);

        if (!CollectionUtils.isEmpty(events)) {
            List<EventTypeEnum> eventTypes = events.stream().map(EventEntity::getType).collect(Collectors.toList());
            checkMbsHarmony(events);
            checkBetIfCompatibleWithEachOther(eventTypes);
            checkEventExpired(events);
            CouponEntity entity = couponRepository.save(CouponEntity.builder()
                    .status(CouponStatusEnum.CREATED)
                    .cost(BigDecimal.valueOf(events.size()*5))
                    .build());

            createSelectionRecords(entity, events);
            CouponDTO response = mapper.map(entity, CouponDTO.class);
            response.setEventIds(couponCreateRequest.getEventIds());
            log.info("******** Incoming User CouponCreateRequest End ********");
            return response;
        }
        else {
            throw new CouponApiException(ErrorCodeEnum.EVENT_NOT_FOUND);
        }
    }

    private void checkEventExpired(List<EventEntity> events) {
        events.stream()
                .filter(eventEntity -> LocalDateTime.now().isAfter(eventEntity.getEventDate()))
                .findAny()
                .ifPresent(eventEntity -> {
                    log.info("EXPIRED_EVENT_DATE");
                    throw new CouponApiException(ErrorCodeEnum.EXPIRED_EVENT_DATE);
                });
    }

    private void checkBetIfCompatibleWithEachOther(List<EventTypeEnum> eventTypes) {
       int countTennisEvent=0;
       int countFootballEvent=0;
        for (EventTypeEnum typeEnum:eventTypes) {
            if(typeEnum.equals(EventTypeEnum.FOOTBALL)){
                countFootballEvent++;
            }
            if(typeEnum.equals(EventTypeEnum.TENNIS)){
                countTennisEvent++;
            }
        }
        if(countFootballEvent >0 && countTennisEvent >0){
            log.info("COMPATIBLE_WITH_EACH_OTHER_BET");
            throw new CouponApiException(ErrorCodeEnum.COMPATIBLE_WITH_EACH_OTHER_BET);
        }
    }
    private void createSelectionRecords(CouponEntity couponEntity, List<EventEntity> events) {
        events.forEach(eventEntity -> {
            CouponSelectionEntity selectionEntity = CouponSelectionEntity.builder()
                    .coupon(couponEntity)
                    .event(eventEntity)
                    .build();

            CouponSelectionEntity couponSelectionEntity = couponSelectionRepository.save(selectionEntity);

            if (CollectionUtils.isEmpty(couponEntity.getSelections())) {
                couponEntity.setSelections(new ArrayList<>());
            }
            couponEntity.getSelections().add(couponSelectionEntity);
        });
    }


    private void checkMbsHarmony(List<EventEntity> events) {
        int count = events.size();
        events.stream()
                .filter(eventEntity -> eventEntity.getMbs() > count)
                .findAny()
                .ifPresent(eventEntity -> {
                    log.info("MBS_ERROR");
                    throw new CouponApiException(ErrorCodeEnum.MBS_ERROR);
                });
    }

    @Transactional
    public List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest) {
        log.info("******** Incoming User CouponPlayRequest Start ********");
        log.info("playCoupons {}", kv("couponPlayRequest", couponPlayRequest));

        List<CouponEntity> couponEntities = couponRepository.findByIdIn(couponPlayRequest.getCouponIds());
        if (!CollectionUtils.isEmpty(couponEntities)) {
            checkCouponsAlreadyPurchased(couponEntities);
            BigDecimal totalCost = calculateTotalCostOfCoupons(couponEntities);
            balanceService.updateBalance(couponPlayRequest.getUserId(), totalCost, createRandomTransactionId(),
                    TransactionTypeEnum.WITHDRAW);
            List<CouponDTO> response = couponEntities.stream()
                    .map(couponEntity -> {
                        couponEntity.setUserId(couponPlayRequest.getUserId());
                        couponEntity.setStatus(CouponStatusEnum.PLAYED);
                        couponEntity.setPlayDate(LocalDateTime.now());
                        couponRepository.save(couponEntity);
                        return mapper.map(couponEntity, CouponDTO.class);
                    })
                    .collect(Collectors.toList());
            log.info("playCoupons-end {}", kv("response", response));
            return response;
        } else {
            throw new CouponApiException(ErrorCodeEnum.COUPON_NOT_FOUND);
        }

    }

    private BigDecimal calculateTotalCostOfCoupons(List<CouponEntity> couponEntityList) {
        return couponEntityList.stream()
                .map(CouponEntity::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private void checkCouponsAlreadyPurchased(List<CouponEntity> coupons) {
        coupons.stream()
                .filter(couponEntity ->
                        couponEntity.getUserId() != null || couponEntity.getStatus() == CouponStatusEnum.PLAYED)
                .findAny()
                .ifPresent(couponEntity -> {
                    throw new CouponApiException(ErrorCodeEnum.COUPON_ALREADY_PURCHASED);
                });
    }

    private String createRandomTransactionId() {
        return UUID.randomUUID().toString();
    }


    public CouponDTO cancelCoupon(Long couponId) {
        log.info("******** Incoming User cancelCoupon Start ********");
        log.info("cancelCoupon-start {}", kv("couponId", couponId));

        Optional<CouponEntity> dbCouponEntity = couponRepository.findByIdAndStatus(couponId, CouponStatusEnum.PLAYED);
        if(dbCouponEntity.isPresent()){
            CouponEntity couponEntity = dbCouponEntity.get();
            checkCouponPlayDate(couponEntity.getPlayDate());
            couponEntity.setStatus(CouponStatusEnum.CANCELLED);
            balanceService.updateBalance(couponEntity.getUserId(), couponEntity.getCost(), createRandomTransactionId(),
                    TransactionTypeEnum.DEPOSIT);
            couponRepository.save(couponEntity);

            CouponDTO response = mapper.map(couponEntity, CouponDTO.class);
            log.info("******** Incoming User cancelCoupon End ********");
            return response;
        }else{
            throw new CouponApiException(ErrorCodeEnum.COUPON_NOT_FOUND);
        }

    }

    private void checkCouponPlayDate(LocalDateTime playDate) {
        if (LocalDateTime.now().minus(Duration.ofMinutes(playDate.getHour()))
                .isAfter(playDate)) {
            throw new CouponApiException(ErrorCodeEnum.COUPON_NOT_FOUND);
        }
    }

    public List<CouponDTO> getPlayedCoupons(Long userId) {
        log.info("******** Incoming User getPlayedCoupons Start ********");
        log.info("getPlayedCoupons-start {}", kv("userId", userId));

        List<CouponDTO> response = couponRepository.findByUserIdAndStatus(userId, CouponStatusEnum.PLAYED).stream()
                .map(entity -> mapper.map(entity, CouponDTO.class))
                .collect(Collectors.toList());

        log.info("******** Incoming User getPlayedCoupons end ********");
        return response;
    }
}
