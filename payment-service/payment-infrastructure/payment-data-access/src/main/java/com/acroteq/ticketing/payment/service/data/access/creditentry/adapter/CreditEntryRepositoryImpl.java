package com.acroteq.ticketing.payment.service.data.access.creditentry.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.data.access.creditentry.entity.CreditEntryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.creditentry.mapper.CreditEntryDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.mapper.CreditEntryJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.creditentry.repository.CreditEntryJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

  private final CreditEntryJpaRepository creditEntryJpaRepository;
  private final CreditEntryJpaToDomainMapper creditEntryJpaToDomainMapper;
  private final CreditEntryDomainToJpaMapper creditEntryDomainToJpaMapper;


  @Override
  public CreditEntry save(final CreditEntry creditEntry) {
    final CreditEntryJpaEntity creditEntryJpaEntity = creditEntryDomainToJpaMapper.convertDomainToEntity(creditEntry);
    final CreditEntryJpaEntity savedEntity = creditEntryJpaRepository.save(creditEntryJpaEntity);
    return creditEntryJpaToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<CreditEntry> findByCustomerId(final CustomerId customerId) {
    return creditEntryJpaRepository.findByCustomerId(customerId.getValue())
                                   .map(creditEntryJpaToDomainMapper::convertEntityToDomain);
  }
}
