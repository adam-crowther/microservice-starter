package com.acroteq.ticketing.approval.service.domain.entity.airline;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.FlightId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Flight extends ReplicatedEntity<FlightId> {

  @NonNull
  private final String flightNumber;
  @NonNull
  private final CashValue price;

  private final boolean available;
}
