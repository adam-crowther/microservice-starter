package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.domain.valueobject.FlightId;

public interface FlightRepository extends ReadRepository<FlightId, Flight> {}
