package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class AirlineCommandProcessor {

  private final AirlineDomainService airlineDomainService;
  private final AirlineRepository airlineRepository;

  Airline createAirline(final Airline airline) {
    log.info("Received create airline command for code {}", airline.getCode());
    airlineDomainService.validateAirline(airline);
    return airlineRepository.save(airline);
  }

  Airline updateAirline(final Airline airline) {
    log.info("Received update airline command for code {}", airline.getCode());
    airlineDomainService.validateAirline(airline);
    return airlineRepository.save(airline);
  }

  void deleteAirline(final String code) {
    log.info("Received delete airline command for code {}", code);
    airlineRepository.deleteByCode(code);
  }
}
