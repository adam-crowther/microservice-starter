package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;

import java.util.Optional;

public interface AirlineRepository {

  Optional<Airline> findById(AirlineId airlineId);

  Airline insert(Airline airline);

  Airline update(Airline airline);

  void deleteById(AirlineId airlineId);
}
