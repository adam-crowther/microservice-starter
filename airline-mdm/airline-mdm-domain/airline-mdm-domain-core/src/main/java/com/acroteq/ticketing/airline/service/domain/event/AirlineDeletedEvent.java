package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.airline.service.domain.event.visitor.AirlineEventVisitor;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class AirlineDeletedEvent extends AirlineEvent {

  private static final String EVENT_TYPE = AirlineDeletedEvent.class.getSimpleName();

  private final AirlineId airlineId;

  @Override
  public <T> T accept(final AirlineEventVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}
