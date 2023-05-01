package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

import java.util.Optional;

public interface AirlineRepository {

  Airline insert(Airline airline);

  Airline update(Airline airline);

  Optional<Airline> findById(AirlineId airlineId);

  void deleteById(AirlineId airlineId);
}
