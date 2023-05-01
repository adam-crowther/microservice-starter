package com.acroteq.ticketing.approval.service.domain.event.airline;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
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