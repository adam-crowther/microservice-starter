package com.acroteq.ticketing.payment.service.data.access.credithistory.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditHistoryDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditHistoryJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.repository.CreditHistoryJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreditHistoryRepositoryImpl
    extends ReadWriteRepositoryImpl<CreditHistoryId, CreditHistory, CreditHistoryJpaEntity>
    implements CreditHistoryRepository {

  private final CreditHistoryJpaRepository jpaRepository;
  private final CreditHistoryJpaToDomainMapper jpaToDomainMapper;

  public CreditHistoryRepositoryImpl(final CreditHistoryJpaRepository jpaRepository,
                                     final CreditHistoryJpaToDomainMapper jpaToDomainMapper,
                                     final CreditHistoryDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<List<CreditHistory>> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
