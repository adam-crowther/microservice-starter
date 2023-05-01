package com.acroteq.ticketing.approval.service.domain.event.airline;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class AirlineUpdatedEvent extends AirlineEvent {

  @NonNull
  private final Airline airline;

  @Override
  public AirlineId getAirlineId() {
    return airline.getId();
  }
}
