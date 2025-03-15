package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;

public interface AirlineRepository extends ReadRepository<AirlineId, Airline>, WriteRepository<AirlineId, Airline> {}
