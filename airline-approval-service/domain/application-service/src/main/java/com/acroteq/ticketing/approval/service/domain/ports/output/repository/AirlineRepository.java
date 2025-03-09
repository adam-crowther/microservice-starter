package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.domain.valueobject.AirlineId;

public interface AirlineRepository extends ReadRepository<AirlineId, Airline>, WriteRepository<AirlineId, Airline> {}
