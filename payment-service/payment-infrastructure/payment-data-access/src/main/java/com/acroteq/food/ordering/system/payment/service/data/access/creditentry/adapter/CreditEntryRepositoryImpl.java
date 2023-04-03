package com.acroteq.food.ordering.system.payment.service.data.access.creditentry.adapter;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.mapper.CreditEntryDomainToEntityMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.repository.CreditEntryJpaRepository;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.entity.CreditEntryEntity;
import com.acroteq.food.ordering.system.payment.service.data.access.creditentry.mapper.CreditEntryEntityToDomainMapper;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

  private final CreditEntryJpaRepository creditEntryJpaRepository;
  private final CreditEntryEntityToDomainMapper creditEntryEntityToDomainMapper;
  private final CreditEntryDomainToEntityMapper creditEntryDomainToEntityMapper;


  @Override
  public CreditEntry save(final CreditEntry creditEntry) {
    final CreditEntryEntity creditEntryEntity = creditEntryDomainToEntityMapper.convertDomainToEntity(creditEntry);
    final CreditEntryEntity savedEntity = creditEntryJpaRepository.save(creditEntryEntity);
    return creditEntryEntityToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<CreditEntry> findByCustomerId(final CustomerId customerId) {
    return creditEntryJpaRepository
        .findByCustomerId(customerId.getValue())
        .map(creditEntryEntityToDomainMapper::convertEntityToDomain);
  }
}
