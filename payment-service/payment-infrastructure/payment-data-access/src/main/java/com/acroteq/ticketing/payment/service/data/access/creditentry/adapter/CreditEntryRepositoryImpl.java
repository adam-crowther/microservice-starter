package com.acroteq.ticketing.payment.service.data.access.creditentry.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.creditentry.mapper.CreditEntryDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.mapper.CreditEntryJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.repository.CreditEntryJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryRepositoryImpl extends ReadWriteRepositoryImpl<CreditEntryId, CreditEntry, CreditEntryJpaEntity>
    implements CreditEntryRepository {

  private final CreditEntryJpaRepository jpaRepository;
  private final CreditEntryJpaToDomainMapper jpaToDomainMapper;

  public CreditEntryRepositoryImpl(final CreditEntryJpaRepository jpaRepository,
                                   final CreditEntryJpaToDomainMapper jpaToDomainMapper,
                                   final CreditEntryDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<CreditEntry> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
