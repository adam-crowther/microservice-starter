package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;

import java.util.Optional;

public interface FlightRepository extends ReadRepository<FlightId, Flight>, WriteRepository<FlightId, Flight> {

  Optional<Flight> findByCode(String code);

  Optional<FlightId> findIdByCode(String code);

  void deleteByCode(String code);
}
