package com.acroteq.ticketing.approval.service.domain.event.airline;

import com.acroteq.domain.event.EntityEvent;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

@ToString
@Builder
@Value
public class AirlineEvent implements EntityEvent {

  @NonNull
  Airline airline;

  @Override
  public Optional<Long> getId() {
    return Optional.of(airline)
                   .map(Airline::getId)
                   .map(EntityId::getValue);
  }

  @Override
  public Optional<Long> getVersion() {
    return Optional.of(airline)
                   .map(Airline::getVersion);
  }
}
