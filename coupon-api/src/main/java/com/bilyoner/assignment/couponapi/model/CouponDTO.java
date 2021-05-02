package com.bilyoner.assignment.couponapi.model;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO implements Serializable {

    private Long id;
    private Long userId;
    private CouponStatusEnum status;
    private BigDecimal cost;
    private List<Long> eventIds;
    private LocalDateTime playDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    @JsonIgnoreProperties("coupon")
    private List<CouponSelectionDTO> selections;
}
