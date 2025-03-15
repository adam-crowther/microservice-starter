package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;

public interface FlightRepository extends ReadRepository<FlightId, Flight> {}
