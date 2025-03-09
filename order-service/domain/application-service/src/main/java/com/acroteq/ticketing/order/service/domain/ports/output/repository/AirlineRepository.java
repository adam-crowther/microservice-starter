package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;

public interface AirlineRepository extends ReadRepository<AirlineId, Airline>, WriteRepository<AirlineId, Airline> {}
