package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.exception.FlightIdNotInAirlineException;
import com.acroteq.ticketing.airline.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
class FlightCommandProcessor {

  private final AirlineDomainService airlineDomainService;
  private final FlightRepository flightRepository;
  private final AirlineRepository airlineRepository;

  Pair<Airline, Flight> createFlight(final String airlineCode, final Flight flight) {
    log.info("Received create flight command");
    airlineDomainService.validateFlight(flight);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));

    final Flight savedFlight = flightRepository.save(flight);
    final List<Flight> updatedFlights = new ArrayList<>(airline.getFlights());
    updatedFlights.add(savedFlight);
    final Airline updatedAirline = airline.toBuilder()
                                          .flights(updatedFlights)
                                          .build();
    final Airline savedAirline = airlineRepository.save(updatedAirline);

    return Pair.of(savedAirline, savedFlight);
  }

  Pair<Airline, Flight> updateFlight(final String airlineCode, final Flight flight) {
    log.info("Received update flight command for symbolic code {}", flight.getCode());
    airlineDomainService.validateFlight(flight);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    validateFlightInAirline(airline, flight);
    final Flight savedFlight = flightRepository.save(flight);
    final List<Flight> updatedFlights = new ArrayList<>(airline.getFlights());
    updatedFlights.add(savedFlight);
    updatedFlights.removeIf(a -> Objects.equals(a.getId(), flight.getId()));
    final Airline updatedAirline = airline.toBuilder()
                                          .flights(updatedFlights)
                                          .build();
    final Airline savedAirline = airlineRepository.save(updatedAirline);

    return Pair.of(savedAirline, savedFlight);
  }

  private void validateFlightInAirline(final Airline airline, final Flight flight) {
    final FlightId flightId = flight.getId();
    final boolean isFlightInAirline = airline.containsFlightWithId(flightId);
    if (!isFlightInAirline) {
      final AirlineId airlineId = airline.getId();
      throw new FlightIdNotInAirlineException(flightId, airlineId);
    }
  }

  void deleteFlight(final String airlineCode, final String flightCode) {
    log.info("Received delete flight command for symbolic code {}", flightCode);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    final Flight flight = flightRepository.findByCode(flightCode)
                                          .orElseThrow(() -> new FlightNotFoundException(flightCode));
    validateFlightInAirline(airline, flight);

    flightRepository.deleteByCode(flightCode);
  }
}
