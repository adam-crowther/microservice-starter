package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

import java.util.Optional;

public interface AirlineRepository {

  Airline save(Airline airline);

  Optional<Airline> findById(AirlineId airlineId);

  Optional<Airline> loadAirline(AirlineId airlineId);

  void deleteById(AirlineId airlineId);
}
