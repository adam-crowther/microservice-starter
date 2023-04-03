package com.acroteq.food.ordering.system.payment.service.data.access.credithistory.adapter;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.entity.CreditHistoryEntity;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.mapper.CreditHistoryDomainToEntityMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.mapper.CreditHistoryEntityToDomainMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.credithistory.repository.CreditHistoryJpaRepository;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

  private final CreditHistoryJpaRepository creditHistoryJpaRepository;
  private final CreditHistoryEntityToDomainMapper creditHistoryEntityToDomainMapper;
  private final CreditHistoryDomainToEntityMapper creditHistoryDomainToEntityMapper;

  @Override
  public CreditHistory save(final CreditHistory creditHistory) {
    final CreditHistoryEntity creditHistoryEntity = creditHistoryDomainToEntityMapper.convertDomainToEntity(
        creditHistory);
    final CreditHistoryEntity savedEntity = creditHistoryJpaRepository.save(creditHistoryEntity);
    return creditHistoryEntityToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<List<CreditHistory>> findByCustomerId(final CustomerId customerId) {
    final Optional<List<CreditHistoryEntity>> creditHistory =
        creditHistoryJpaRepository.findByCustomerId(customerId.getValue());
    return creditHistory
        .map(creditHistoryList ->
                 creditHistoryList.stream()
                                  .map(creditHistoryEntityToDomainMapper::convertEntityToDomain)
                                  .collect(Collectors.toList()));
  }
}
