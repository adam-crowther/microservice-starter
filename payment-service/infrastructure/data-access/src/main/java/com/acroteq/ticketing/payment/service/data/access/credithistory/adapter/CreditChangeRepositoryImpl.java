package com.acroteq.ticketing.payment.service.data.access.credithistory.adapter;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditChangeJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.repository.CreditChangeJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditChangeRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreditChangeRepositoryImpl
    extends ReadWriteRepositoryImpl<CreditChangeId, CreditChange, CreditChangeJpaEntity>
    implements CreditChangeRepository {

  private final CreditChangeJpaRepository jpaRepository;
  private final CreditChangeJpaMapper jpaMapper;

  public CreditChangeRepositoryImpl(
      final CreditChangeJpaRepository jpaRepository, final CreditChangeJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public Optional<List<CreditChange>> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaMapper::convert);
  }
}
