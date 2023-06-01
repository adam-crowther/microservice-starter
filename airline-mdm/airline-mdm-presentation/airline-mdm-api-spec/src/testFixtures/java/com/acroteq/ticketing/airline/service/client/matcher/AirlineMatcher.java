package com.acroteq.ticketing.airline.service.client.matcher;

import com.acroteq.ticketing.airline.service.client.model.Airline;
import com.acroteq.ticketing.airline.service.client.model.CashValue;
import com.acroteq.ticketing.airline.service.client.model.CashValueCommand;
import com.acroteq.ticketing.airline.service.client.model.CreateAirlineCommand;
import com.acroteq.ticketing.airline.service.client.model.CreateFlightCommand;
import com.acroteq.ticketing.airline.service.client.model.Flight;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class AirlineMatcher extends TypeSafeMatcher<Airline> {

  private static final double DOUBLE_COMPARISON_EPSILON = 10E-6;
  private final CreateAirlineCommand request;

  public static AirlineMatcher matches(final CreateAirlineCommand request) {
    return new AirlineMatcher(request);
  }

  @Override
  protected boolean matchesSafely(final Airline airline) {

    return Objects.equals(airline.getName(), request.getName())
           && Objects.equals(airline.getActive(), request.getActive())
           && flightsMatch(airline.getFlights(), request.getFlights());
  }

  private boolean flightsMatch(final List<Flight> airline, final List<CreateFlightCommand> request) {
    return airline.size() == request.size() && airline.stream()
                                                      .allMatch(hasMatchingFlight(request));
  }

  private Predicate<Flight> hasMatchingFlight(final List<CreateFlightCommand> request) {
    return flight -> hasMatchingFlight(flight, request);
  }

  private boolean hasMatchingFlight(final Flight flight, final List<CreateFlightCommand> request) {
    return request.stream()
                  .anyMatch(isMatchingFlight(flight));
  }

  private Predicate<CreateFlightCommand> isMatchingFlight(final Flight flight) {
    return requestFlight -> isMatchingFlight(requestFlight, flight);
  }

  private boolean isMatchingFlight(final CreateFlightCommand requestFlight, final Flight flight) {
    final CashValueCommand requestPrice = requestFlight.getPrice();
    final CashValue flightPrice = flight.getPrice();
    return Objects.equals(requestFlight.getFlightNumber(), flight.getFlightNumber())
           && Objects.equals(requestFlight.getAvailable(), flight.getAvailable())
           && Precision.equals(requestPrice.getAmount(), flightPrice.getAmount(), DOUBLE_COMPARISON_EPSILON)
           && Objects.equals(requestPrice.getCurrencyId(), flightPrice.getCurrencyId());
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
  }
}
