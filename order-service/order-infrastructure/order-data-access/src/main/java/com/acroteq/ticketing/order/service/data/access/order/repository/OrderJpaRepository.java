package com.acroteq.ticketing.order.service.data.access.order.repository;

import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {

  Optional<OrderJpaEntity> findByTrackingId(UUID trackingId);
}
