package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Flight;

public interface FlightRepository extends ReadRepository<FlightId, Flight> {}
