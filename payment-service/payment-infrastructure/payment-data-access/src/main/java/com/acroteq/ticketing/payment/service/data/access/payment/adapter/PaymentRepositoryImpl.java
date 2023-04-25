package com.acroteq.ticketing.payment.service.data.access.payment.adapter;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.payment.mapper.PaymentDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.mapper.PaymentJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.repository.PaymentJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentJpaRepository paymentJpaRepository;
  private final PaymentJpaToDomainMapper paymentJpaToDomainMapper;
  private final PaymentDomainToJpaMapper paymentDomainToJpaMapper;

  @Override
  public Payment save(final Payment payment) {
    final PaymentJpaEntity paymentJpaEntity = paymentDomainToJpaMapper.convertDomainToJpa(payment);
    final PaymentJpaEntity savedEntity = paymentJpaRepository.save(paymentJpaEntity);
    return paymentJpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  @Override
  public Optional<Payment> findByOrderId(final OrderId orderId) {
    return paymentJpaRepository.findByOrderId(orderId.getValue())
                               .map(paymentJpaToDomainMapper::convertJpaToDomain);
  }
}
