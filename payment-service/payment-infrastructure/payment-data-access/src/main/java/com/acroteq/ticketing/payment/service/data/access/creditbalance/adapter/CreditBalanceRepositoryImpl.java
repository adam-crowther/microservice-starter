package com.acroteq.ticketing.payment.service.data.access.creditbalance.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.entity.CreditBalanceJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper.CreditBalanceDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper.CreditBalanceJpaToDomainMapper;
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
  private final CreditBalanceJpaToDomainMapper jpaToDomainMapper;

  public CreditBalanceRepositoryImpl(final CreditBalanceJpaRepository jpaRepository,
                                     final CreditBalanceJpaToDomainMapper jpaToDomainMapper,
                                     final CreditBalanceDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<CreditBalance> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
