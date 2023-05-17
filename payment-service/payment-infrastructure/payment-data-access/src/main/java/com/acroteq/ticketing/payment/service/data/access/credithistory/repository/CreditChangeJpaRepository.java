package com.acroteq.ticketing.payment.service.data.access.credithistory.repository;

import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditChangeJpaRepository extends JpaRepository<CreditChangeJpaEntity, Long> {

  Optional<List<CreditChangeJpaEntity>> findByCustomerId(Long customerId);
}
