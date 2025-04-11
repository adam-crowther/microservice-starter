package com.acroteq.ticketing.airline.service.domain.entity;

import static com.acroteq.precondition.Precondition.checkPrecondition;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.domain.entity.AggregateRoot;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineValidationException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Airline extends AggregateRoot<AirlineId> {

  @NonNull
  private final String code;
  @NonNull
  private final String name;
  private final boolean active;
  @NonNull
  private final List<Flight> flights;

  public void validate() {
    checkPrecondition(isNotBlank(code), AirlineValidationException::new, "code");
    checkPrecondition(isNotBlank(name), AirlineValidationException::new, "name");
    flights.forEach(Flight::validate);
  }

  public boolean containsFlightWithId(final FlightId flightId) {
    return flights.stream()
                  .map(Flight::getId)
                  .anyMatch(a -> Objects.equals(a, flightId));
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineBuilder<C extends Airline, B extends AirlineBuilder<C, B>>
      extends AggregateRootBuilder<AirlineId, C, B> {

    public B addFlight(@NonNull final Flight flight) {
      this.flights.add(flight);
      return this.self();
    }
  }
}
