package com.acroteq.ticketing.approval.service.domain.event.airline;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class AirlineDeletedEvent extends AirlineEvent {

  private static final String EVENT_TYPE = AirlineDeletedEvent.class.getSimpleName();

  @NonNull
  private final AirlineId airlineId;

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}
