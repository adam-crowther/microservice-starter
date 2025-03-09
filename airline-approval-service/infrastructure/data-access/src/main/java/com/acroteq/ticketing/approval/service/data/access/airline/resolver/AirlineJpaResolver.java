package com.acroteq.ticketing.approval.service.data.access.airline.resolver;

import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.infrastructure.data.access.resolver.JpaResolver;
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
