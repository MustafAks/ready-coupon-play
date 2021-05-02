package com.bilyoner.assignment.couponapi.service;


import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;

public interface BalanceService {

    void updateBalance(Long userId, BigDecimal amount, String transactionId, TransactionTypeEnum transactionType);

}
