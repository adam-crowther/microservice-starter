package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;

public interface AirlineDomainService {

  void validateAirline(Airline airline);
}
