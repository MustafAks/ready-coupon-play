package com.bilyoner.assignment.couponapi.entity;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CouponEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatusEnum status;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime playDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;
}
