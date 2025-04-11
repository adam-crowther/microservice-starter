package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;

import java.util.Optional;

public interface FlightRepository extends ReadRepository<FlightId, Flight> {

  Optional<Flight> findByCode(String code);
}
