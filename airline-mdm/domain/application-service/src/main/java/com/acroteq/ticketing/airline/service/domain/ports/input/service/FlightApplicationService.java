package com.acroteq.ticketing.airline.service.domain.ports.input.service;

import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface FlightApplicationService {

  List<Flight> loadAllFlightsByAirlineCode(String airlineCode);

  Optional<Flight> loadFlight(String flightCode);

  Optional<FlightId> fetchFlightId(String flightCode);

  Flight createFlight(String airlineCode, @Valid Flight airline);

  void updateFlight(String airlineCode, @Valid Flight airline);

  void deleteFlight(String airlineCode, String flightCode);
}
