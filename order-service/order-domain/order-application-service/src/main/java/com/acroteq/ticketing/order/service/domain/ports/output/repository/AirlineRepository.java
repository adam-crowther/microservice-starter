package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;

import java.util.Optional;

public interface AirlineRepository {

  Optional<Airline> findAirline(AirlineId airlineId);

  Airline save(Airline airline);

  void delete(AirlineId airlineId);
}
