package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.visitor.AirlineEventVisitor;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class AirlineUpdateFailedEvent extends AirlineEvent {

  private static final String EVENT_TYPE = AirlineUpdateFailedEvent.class.getSimpleName();

  private final Airline airline;

  @Override
  public AirlineId getAirlineId() {
    return airline.getId();
  }

  @Override
  public <T> T accept(final AirlineEventVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}
