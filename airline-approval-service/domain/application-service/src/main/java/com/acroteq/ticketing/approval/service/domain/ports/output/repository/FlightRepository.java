package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.domain.valueobject.FlightId;

public interface FlightRepository extends ReadRepository<FlightId, Flight> {}
