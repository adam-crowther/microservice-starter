package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.exception.FlightNotInAirlineException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class FlightCommandProcessor {

  private final AirlineDomainService airlineDomainService;
  private final AirlineRepository airlineRepository;

  Pair<Airline, Flight> createFlight(final String airlineCode, final Flight flight) {
    final String flightCode = flight.getCode();
    log.info("Received create flight {} in airline {}", flightCode, airlineCode);
    airlineDomainService.validateFlight(flight);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));

    final Airline updatedAirline = airline.withAddedFlight(flight);
    final Airline savedAirline = airlineRepository.save(updatedAirline);
    final Flight savedFlight = savedAirline.getFlightWithCode(flightCode)
                                           .orElseThrow(() -> new FlightNotInAirlineException(flightCode, airlineCode));

    return Pair.of(savedAirline, savedFlight);
  }

  Pair<Airline, Flight> updateFlight(final String airlineCode, final Flight flight) {
    final String flightCode = flight.getCode();
    log.info("Received update flight {} in airline {}", flightCode, airlineCode);
    airlineDomainService.validateFlight(flight);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    final Airline updatedAirline = airline.withUpdatedFlight(flight);
    final Airline savedAirline = airlineRepository.save(updatedAirline);
    final Flight savedFlight = savedAirline.getFlightWithCode(flightCode)
                                           .orElseThrow(() -> new FlightNotInAirlineException(flightCode, airlineCode));

    return Pair.of(savedAirline, savedFlight);
  }

  void deleteFlight(final String airlineCode, final String flightCode) {
    log.info("Received delete flight {} in airline {}", flightCode, airlineCode);
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    final Airline updatedAirline = airline.withRemovedFlight(flightCode);
    airlineRepository.save(updatedAirline);
  }
}
