package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class AirlineDeletedEvent extends AirlineEvent {

  private final AirlineId airlineId;
}
