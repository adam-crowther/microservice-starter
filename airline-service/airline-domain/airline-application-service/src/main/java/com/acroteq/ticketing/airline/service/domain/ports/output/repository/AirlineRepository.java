package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

import java.util.Optional;

public interface AirlineRepository {

  Optional<Airline> loadAirline(AirlineId airlineId);
}
