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

  @NonNull
  private final AirlineId airlineId;
}
