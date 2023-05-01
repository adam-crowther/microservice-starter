package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.domain.event.DomainEvent;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class AirlineEvent extends DomainEvent<Airline> {

  public abstract AirlineId getAirlineId();
}
