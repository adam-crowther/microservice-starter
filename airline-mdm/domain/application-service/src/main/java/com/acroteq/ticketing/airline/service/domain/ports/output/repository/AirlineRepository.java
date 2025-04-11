package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;

import java.util.Optional;

public interface AirlineRepository extends ReadRepository<AirlineId, Airline>, WriteRepository<AirlineId, Airline> {

  Optional<Airline> findByCode(String code);

  Optional<AirlineId> findIdByCode(String code);

  void deleteByCode(String code);
}
