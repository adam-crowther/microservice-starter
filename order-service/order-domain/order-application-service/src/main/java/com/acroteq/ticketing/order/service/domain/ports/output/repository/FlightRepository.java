package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;

import java.util.Optional;

public interface FlightRepository {

  Optional<Flight> findById(FlightId flightId);
}
