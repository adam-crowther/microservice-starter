package com.acroteq.ticketing.payment.service.data.access.payment.repository;

import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {

  Optional<PaymentJpaEntity> findByOrderId(Long orderId);
}
