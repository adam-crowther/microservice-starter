package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;

public interface AirlineDomainService {

  void validateAirline(Airline airline);

  void validateFlight(Flight flight);
}
