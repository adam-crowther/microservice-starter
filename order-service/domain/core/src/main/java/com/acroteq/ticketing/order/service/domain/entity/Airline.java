package com.acroteq.ticketing.order.service.domain.entity;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.FlightId;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Airline extends ReplicatedEntity<AirlineId> {

  @NonNull
  private final String code;

  @NonNull
  private final String name;

  @NonNull
  private final List<Flight> flights;

  private boolean active;

  public Map<FlightId, Flight> getFlightMap() {
    return flights.stream()
                  .collect(toMap(Flight::getId, identity()));
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineBuilder<C extends Airline, B extends AirlineBuilder<C, B>>
      extends ReplicatedEntityBuilder<AirlineId, C, B> {

    public B flights(@Nullable final List<Flight> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
