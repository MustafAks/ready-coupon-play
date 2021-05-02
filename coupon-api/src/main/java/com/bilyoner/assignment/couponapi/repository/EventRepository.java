package com.bilyoner.assignment.couponapi.repository;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByIdIn(List<Long> eventIds);
}
