package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Flight;

import java.util.Optional;

public interface AirlineFlightRepository {

  Optional<Airline> loadAirline(AirlineId airlineId);

  Optional<Flight> loadFlight(FlightId flightId);
}
