package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.domain.valueobject.FlightId;

import java.util.Optional;

public interface FlightRepository {

  Optional<Flight> findById(FlightId id);
}
