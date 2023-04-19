package com.acroteq.ticketing.airline.service.domain.entity;

import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Airline extends AggregateRoot<AirlineId> {

  private final boolean active;

  private final ImmutableList<Flight> flights;

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineBuilder<C extends Airline, B extends Airline.AirlineBuilder<C, B>>
      extends AggregateRoot.AggregateRootBuilder<AirlineId, C, B> {

    public B flights(final List<Flight> flights) {
      this.flights = ImmutableList.copyOf(flights);
      return this.self();
    }
  }
}
