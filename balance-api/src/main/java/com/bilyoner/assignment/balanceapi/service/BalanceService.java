package com.bilyoner.assignment.balanceapi.service;

import com.bilyoner.assignment.balanceapi.exception.BalanceApiException;
import com.bilyoner.assignment.balanceapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceHistoryEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceHistoryRepository;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService  {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;
    private final ModelMapper mapper;



    @Transactional
    public void updateBalance(UpdateBalanceRequest updateBalanceRequest) {
        log.info("******** Incoming User UpdateBalanceRequest Start ********");
        log.info("updateBalanceRequest {}", kv("updateBalanceRequest", updateBalanceRequest));

        Long userId = updateBalanceRequest.getUserId();
        Optional<UserBalanceEntity> dbUserBalance = userBalanceRepository.findById(userId);
        if (dbUserBalance.isPresent()) {
            UserBalanceEntity entity = dbUserBalance.get();
            BigDecimal currentBalance = entity.getAmount();
            BigDecimal newBalance = calculateNewBalance(currentBalance, updateBalanceRequest.getAmount(),
                    updateBalanceRequest.getTransactionType());
            entity.setAmount(newBalance);
            UserBalanceHistoryEntity historyRecord = prepareHistoryRecord(updateBalanceRequest, currentBalance,
                    newBalance, entity);
            userBalanceRepository.save(entity);
            userBalanceHistoryRepository.save(historyRecord);
            log.info("******** Incoming User UpdateBalanceRequest End ********");
        } else {
            throw new BalanceApiException(ErrorCodeEnum.USER_NOT_FOUND);
        }
    }

    private BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal balanceChange, TransactionTypeEnum transactionType) {
        BigDecimal newBalance = currentBalance;
        switch (transactionType) {
            case DEPOSIT:
                newBalance = newBalance.add(balanceChange);
                break;
            case WITHDRAW:
                adequateBalanceControl(currentBalance, balanceChange);
                newBalance = newBalance.subtract(balanceChange);
                break;
        }
        return newBalance;
    }


    private void adequateBalanceControl(BigDecimal currentBalance, BigDecimal balanceChange) {
        int balanceComparison = currentBalance.compareTo(balanceChange);
        if (balanceComparison == -1) {
            throw new BalanceApiException(ErrorCodeEnum.INSUFFICIENT_BALANCE);
        }
    }

    private UserBalanceHistoryEntity prepareHistoryRecord(UpdateBalanceRequest updateBalanceRequest,
                                                        BigDecimal previousBalance, BigDecimal newBalance,
                                                        UserBalanceEntity userBalanceEntity) {
        UserBalanceHistoryEntity entity = mapper.map(updateBalanceRequest, UserBalanceHistoryEntity.class);
        entity.setPreviousBalance(previousBalance);
        entity.setNewBalance(newBalance);
        entity.setUserBalanceEntity(userBalanceEntity);
        return entity;
    }


}

