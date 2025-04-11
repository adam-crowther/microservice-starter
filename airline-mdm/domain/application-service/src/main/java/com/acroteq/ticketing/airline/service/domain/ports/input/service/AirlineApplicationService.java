package com.acroteq.ticketing.airline.service.domain.ports.input.service;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface AirlineApplicationService {

  List<Airline> loadAllAirlines();

  Optional<Airline> loadAirline(String code);

  Optional<AirlineId> fetchAirlineId(String code);

  Airline createAirline(@Valid Airline airline);

  void updateAirline(@Valid Airline airline);

  void deleteAirline(String code);
}
