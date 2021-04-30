package com.bilyoner.assignment.balanceapi.persistence.entity;

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
public class UserBalanceHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * TODO : Implement missing parts
     */
}
