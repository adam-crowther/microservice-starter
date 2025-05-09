package com.acroteq.ticketing.payment.service.data.access.creditbalance.adapter;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper.CreditBalanceJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.repository.CreditBalanceJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditBalanceRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditBalanceRepositoryImpl
    extends ReadWriteRepositoryImpl<CreditBalanceId, CreditBalance, CreditBalanceJpaEntity>
    implements CreditBalanceRepository {

  private final CreditBalanceJpaRepository jpaRepository;
  private final CreditBalanceJpaMapper jpaMapper;

  public CreditBalanceRepositoryImpl(
      final CreditBalanceJpaRepository jpaRepository, final CreditBalanceJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public Optional<CreditBalance> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaMapper::convert);
  }
}
