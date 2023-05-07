package com.acroteq.ticketing.payment.service.data.access.customer.resolver;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.resolver.JpaResolver;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerJpaResolver implements JpaResolver<CustomerId, CustomerJpaEntity> {

  private final CustomerJpaRepository repository;

  @Override
  public CustomerJpaEntity resolve(final CustomerId customerId) {
    return repository.getReferenceById(customerId.getValue());
  }
}
