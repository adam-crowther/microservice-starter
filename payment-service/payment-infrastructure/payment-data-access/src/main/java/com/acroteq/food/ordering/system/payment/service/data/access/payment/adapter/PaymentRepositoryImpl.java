package com.acroteq.food.ordering.system.payment.service.data.access.payment.adapter;

import com.acroteq.food.ordering.system.payment.service.data.access.payment.entity.PaymentEntity;
import com.acroteq.food.ordering.system.payment.service.data.access.payment.mapper.PaymentDomainToEntityMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.payment.mapper.PaymentEntityToDomainMapper;
import com.acroteq.food.ordering.system.payment.service.data.access.payment.repository.PaymentJpaRepository;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentJpaRepository paymentJpaRepository;
  private final PaymentEntityToDomainMapper paymentEntityToDomainMapper;
  private final PaymentDomainToEntityMapper paymentDomainToEntityMapper;

  @Override
  public Payment save(final Payment payment) {
    final PaymentEntity paymentEntity = paymentDomainToEntityMapper.convertDomainToEntity(payment);
    final PaymentEntity savedEntity = paymentJpaRepository.save(paymentEntity);
    return paymentEntityToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<Payment> findByOrderId(final UUID orderId) {
    return paymentJpaRepository.findByOrderId(orderId)
                               .map(paymentEntityToDomainMapper::convertEntityToDomain);
  }
}
