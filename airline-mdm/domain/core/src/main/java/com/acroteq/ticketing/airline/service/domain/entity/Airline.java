package com.acroteq.ticketing.airline.service.domain.entity;

import static com.acroteq.helper.StreamHelper.toSingleItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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

  public static Predicate<Airline> hasCode(final String code) {
    return airline -> airline.code.equals(code);
  }

  public Optional<Flight> getFlightWithCode(final String flightCode) {
    return flights.stream()
                  .filter(flight -> flight.getCode()
                                          .equals(flightCode))
                  .reduce(toSingleItem());
  }

  public boolean containsFlightWithId(final FlightId flightId) {
    return flights.stream()
                  .map(Flight::getId)
                  .anyMatch(a -> Objects.equals(a, flightId));
  }

  public Airline withAddedFlight(final Flight flight) {
    final List<Flight> updatedFlights = new ArrayList<>(flights);
    updatedFlights.add(flight);
    return toBuilder().flights(updatedFlights)
                      .build();
  }

  public Airline withUpdatedFlight(final Flight flight) {
    final List<Flight> updatedFlights = new ArrayList<>(flights);
    final String flightCode = flight.getCode();
    updatedFlights.removeIf(Flight.hasCode(flightCode));
    updatedFlights.add(flight);
    return toBuilder().flights(updatedFlights)
                      .build();
  }

  public Airline withRemovedFlight(final String flightCode) {
    final List<Flight> updatedFlights = new ArrayList<>(flights);
    updatedFlights.removeIf(Flight.hasCode(flightCode));
    return toBuilder().flights(updatedFlights)
                      .build();
  }
}
