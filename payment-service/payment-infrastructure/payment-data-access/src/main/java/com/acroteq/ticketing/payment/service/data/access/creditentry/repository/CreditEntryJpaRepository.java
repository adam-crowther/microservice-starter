package com.acroteq.ticketing.payment.service.data.access.creditentry.repository;

import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryJpaEntity, Long> {

  Optional<CreditEntryJpaEntity> findByCustomerId(Long customerId);
}
