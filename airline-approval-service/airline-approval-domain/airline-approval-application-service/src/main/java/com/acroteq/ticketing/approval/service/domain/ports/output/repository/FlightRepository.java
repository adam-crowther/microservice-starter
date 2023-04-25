package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.domain.valueobject.FlightId;

import java.util.Optional;

public interface FlightRepository {

  Optional<Flight> loadFlight(FlightId id);
}
