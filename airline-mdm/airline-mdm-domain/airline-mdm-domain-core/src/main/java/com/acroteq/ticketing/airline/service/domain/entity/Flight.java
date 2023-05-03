package com.acroteq.ticketing.airline.service.domain.entity;

import com.acroteq.ticketing.domain.entity.MasterEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Flight extends MasterEntity<FlightId> {

  private final String flightNumber;
  private final CashValue price;
  private final boolean available;
}
