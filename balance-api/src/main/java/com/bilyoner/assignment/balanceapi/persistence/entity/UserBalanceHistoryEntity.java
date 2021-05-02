package com.bilyoner.assignment.balanceapi.persistence.entity;

import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    private BigDecimal previousBalance;

    private BigDecimal newBalance;

    private String transactionId;

    private TransactionTypeEnum transactionType;

    @CreatedDate
    @Column()
    private Timestamp createDate;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserBalanceEntity userBalanceEntity;
}
