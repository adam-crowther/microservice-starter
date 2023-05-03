package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.domain.event.EntityEvent;
import com.acroteq.ticketing.domain.valueobject.BaseId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

@ToString
@Builder
@Value
public class AirlineEvent implements EntityEvent {

  @NonNull Airline airline;

  @Override
  public Optional<Long> getId() {
    return Optional.of(airline)
                   .map(Airline::getId)
                   .map(BaseId::getValue);
  }
}
