package com.acroteq.ticketing.payment.service.data.access.credithistory.repository;

import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditHistoryJpaRepository extends JpaRepository<CreditHistoryJpaEntity, Long> {

  Optional<List<CreditHistoryJpaEntity>> findByCustomerId(Long customerId);
}
