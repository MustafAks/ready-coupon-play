package com.bilyoner.assignment.couponapi.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CouponSelectionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;
}
