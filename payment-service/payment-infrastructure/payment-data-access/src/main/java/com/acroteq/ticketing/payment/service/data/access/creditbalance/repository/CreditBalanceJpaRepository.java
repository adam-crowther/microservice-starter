package com.acroteq.ticketing.payment.service.data.access.creditbalance.repository;

import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditBalanceJpaRepository extends JpaRepository<CreditBalanceJpaEntity, Long> {

  Optional<CreditBalanceJpaEntity> findByCustomerId(Long customerId);
}
