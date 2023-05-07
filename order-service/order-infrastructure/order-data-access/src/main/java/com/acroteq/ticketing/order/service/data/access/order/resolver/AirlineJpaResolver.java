package com.acroteq.ticketing.order.service.data.access.order.resolver;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.infrastructure.data.access.resolver.JpaResolver;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirlineJpaResolver implements JpaResolver<AirlineId, AirlineJpaEntity> {

  private final AirlineJpaRepository repository;

  @Override
  public AirlineJpaEntity resolve(final AirlineId airlineId) {
    return repository.getReferenceById(airlineId.getValue());
  }
}
