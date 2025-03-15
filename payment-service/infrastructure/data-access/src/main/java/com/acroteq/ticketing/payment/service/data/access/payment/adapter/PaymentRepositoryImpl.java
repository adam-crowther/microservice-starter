package com.acroteq.ticketing.payment.service.data.access.payment.adapter;

import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.domain.valueobject.PaymentId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.payment.mapper.PaymentDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.mapper.PaymentJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.repository.PaymentJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentRepositoryImpl extends ReadWriteRepositoryImpl<PaymentId, Payment, PaymentJpaEntity>
    implements PaymentRepository {

  private final PaymentJpaRepository jpaRepository;
  private final PaymentJpaToDomainMapper jpaToDomainMapper;

  public PaymentRepositoryImpl(final PaymentJpaRepository jpaRepository,
                               final PaymentJpaToDomainMapper jpaToDomainMapper,
                               final PaymentDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<Payment> findByOrderId(final OrderId orderId) {
    return jpaRepository.findByOrderId(orderId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
