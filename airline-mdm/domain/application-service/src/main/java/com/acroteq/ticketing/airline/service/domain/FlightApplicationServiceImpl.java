package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.FlightApplicationService;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class FlightApplicationServiceImpl implements FlightApplicationService {

  private final FlightCommandProcessor commandProcessor;
  private final FlightRepository flightRepository;
  private final AirlineRepository airlineRepository;

  private final AirlineEventMessagePublisher eventPublisher;

  @Override
  @Transactional(readOnly = true)
  public List<Flight> loadAllFlightsByAirlineCode(final String airlineCode) {
    final Airline airline = airlineRepository.findByCode(airlineCode)
                                             .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    return airline.getFlights();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Flight> loadFlight(final String flightCode) {
    return flightRepository.findByCode(flightCode);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<FlightId> fetchFlightId(final String flightCode) {
    return flightRepository.findIdByCode(flightCode);
  }

  @Override
  @Transactional
  public Flight createFlight(final String airlineCode, final Flight flight) {
    final Pair<Airline, Flight> airlineFlightPair = commandProcessor.createFlight(airlineCode, flight);
    final Airline savedAirline = airlineFlightPair.getLeft();
    final Flight savedFlight = airlineFlightPair.getRight();

    final AirlineEvent event = AirlineEvent.builder()
                                           .airline(savedAirline)
                                           .build();
    eventPublisher.publish(event);
    return savedFlight;
  }

  @Override
  @Transactional
  public void updateFlight(final String airlineCode, final Flight flight) {
    final Pair<Airline, Flight> airlineFlightPair = commandProcessor.updateFlight(airlineCode, flight);
    final Airline savedAirline = airlineFlightPair.getLeft();
    final AirlineEvent event = AirlineEvent.builder()
                                           .airline(savedAirline)
                                           .build();
    eventPublisher.publish(event);
  }

  @Override
  @Transactional
  public void deleteFlight(final String airlineCode, final String flightCode) {
    commandProcessor.deleteFlight(airlineCode, flightCode);
    eventPublisher.publishDelete(flightCode);
  }
}
