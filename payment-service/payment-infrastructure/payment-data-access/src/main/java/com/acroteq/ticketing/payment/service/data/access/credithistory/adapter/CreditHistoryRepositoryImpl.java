package com.acroteq.ticketing.payment.service.data.access.credithistory.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditHistoryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditHistoryDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditHistoryJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.repository.CreditHistoryJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

  private final CreditHistoryJpaRepository jpaRepository;
  private final CreditHistoryJpaToDomainMapper entityToDomainMapper;
  private final CreditHistoryDomainToJpaMapper domainToEntityMapper;

  @Override
  public CreditHistory save(final CreditHistory creditHistory) {
    final CreditHistoryJpaEntity creditHistoryJpaEntity = domainToEntityMapper.convertDomainToEntity(creditHistory);
    final CreditHistoryJpaEntity savedEntity = jpaRepository.save(creditHistoryJpaEntity);
    return entityToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<List<CreditHistory>> findByCustomerId(final CustomerId customerId) {
    final Optional<List<CreditHistoryJpaEntity>> creditHistory = jpaRepository.findByCustomerId(customerId.getValue());
    return creditHistory.map(entityToDomainMapper::convertEntityToDomain);
  }
}
