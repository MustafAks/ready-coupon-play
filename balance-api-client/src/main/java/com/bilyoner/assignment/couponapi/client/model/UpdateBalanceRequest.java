package com.bilyoner.assignment.couponapi.client.model;

import com.bilyoner.assignment.couponapi.client.model.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBalanceRequest {

    private Long userId;

    private BigDecimal amount;

    private String transactionId;

    private TransactionTypeEnum transactionType;

}
