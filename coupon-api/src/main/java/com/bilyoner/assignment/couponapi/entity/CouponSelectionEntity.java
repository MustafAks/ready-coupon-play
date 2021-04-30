package com.bilyoner.assignment.couponapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    /**
     * TODO : Implement missing parts
     */
}
