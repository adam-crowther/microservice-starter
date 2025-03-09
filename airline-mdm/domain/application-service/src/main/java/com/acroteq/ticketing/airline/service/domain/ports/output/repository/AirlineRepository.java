package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

public interface AirlineRepository extends ReadRepository<AirlineId, Airline>, WriteRepository<AirlineId, Airline> {}
