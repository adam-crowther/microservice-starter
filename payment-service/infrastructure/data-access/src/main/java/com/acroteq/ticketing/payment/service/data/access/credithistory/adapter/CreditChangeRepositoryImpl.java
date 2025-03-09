package com.acroteq.ticketing.payment.service.data.access.credithistory.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.credithistory.entity.CreditChangeJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditChangeDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.credithistory.mapper.CreditChangeJpaToDomainMapper;
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
  private final CreditChangeJpaToDomainMapper jpaToDomainMapper;

  public CreditChangeRepositoryImpl(final CreditChangeJpaRepository jpaRepository,
                                    final CreditChangeJpaToDomainMapper jpaToDomainMapper,
                                    final CreditChangeDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<List<CreditChange>> findByCustomerId(final CustomerId customerId) {
    return jpaRepository.findByCustomerId(customerId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
