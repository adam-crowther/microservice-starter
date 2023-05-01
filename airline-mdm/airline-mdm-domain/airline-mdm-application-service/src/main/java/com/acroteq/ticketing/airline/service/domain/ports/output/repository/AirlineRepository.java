package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

import java.util.Optional;

public interface AirlineRepository {

  Airline save(Airline airline);

  Optional<Airline> findById(AirlineId airlineId);

  void deleteById(AirlineId airlineId);
}
