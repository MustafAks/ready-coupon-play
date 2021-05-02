package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.client.BalancesApiClient;
import com.bilyoner.assignment.couponapi.client.model.UpdateBalanceRequest;
import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalancesApiClient client;

    public void updateBalance(Long userId, BigDecimal amount, String transactionId,
                              TransactionTypeEnum transactionType) {
        client.updateBalance(UpdateBalanceRequest.builder()
                .userId(userId)
                .amount(amount)
                .transactionId(transactionId)
                .transactionType(transactionType)
                .build());

        log.info("updateBalance-end");
    }
}
