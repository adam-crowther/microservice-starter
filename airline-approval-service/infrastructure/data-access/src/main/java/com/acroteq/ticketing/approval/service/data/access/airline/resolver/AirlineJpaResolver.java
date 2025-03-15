package com.acroteq.ticketing.approval.service.data.access.airline.resolver;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.infrastructure.data.access.resolver.JpaResolver;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.AirlineJpaRepository;
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
