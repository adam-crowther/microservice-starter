package com.acroteq.ticketing.order.service.data.access.order.resolver;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.infrastructure.data.access.resolver.JpaResolver;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.repository.CustomerJpaRepository;
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
