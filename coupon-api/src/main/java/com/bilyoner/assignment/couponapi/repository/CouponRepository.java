package com.bilyoner.assignment.couponapi.repository;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
}
