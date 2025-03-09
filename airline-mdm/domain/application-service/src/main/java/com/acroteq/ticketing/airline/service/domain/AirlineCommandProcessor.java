package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.mapper.CreateAirlineDtoToDomainMapper;
import com.acroteq.ticketing.airline.service.domain.mapper.UpdateAirlineDtoToDomainMapper;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class AirlineCommandProcessor {

  private final AirlineDomainService airlineDomainService;
  private final AirlineRepository airlineRepository;
  private final CreateAirlineDtoToDomainMapper createAirlineMapper;
  private final UpdateAirlineDtoToDomainMapper updateAirlineMapper;

  AirlineEvent createAirline(final CreateAirlineCommandDto createAirlineCommandDto) {
    log.info("Received create airline command");
    final Airline airline = createAirlineMapper.convertDtoToDomain(createAirlineCommandDto);
    airlineDomainService.validateAirline(airline);
    final Airline savedAirline = airlineRepository.save(airline);

    return AirlineEvent.builder()
                       .airline(savedAirline)
                       .build();
  }

  AirlineEvent updateAirline(final UpdateAirlineCommandDto updateAirlineCommandDto) {
    log.info("Received update airline command for id {}", updateAirlineCommandDto.getId());
    final Airline airline = updateAirlineMapper.convertDtoToDomain(updateAirlineCommandDto);
    airlineDomainService.validateAirline(airline);
    final Airline savedAirline = airlineRepository.save(airline);

    return AirlineEvent.builder()
                       .airline(savedAirline)
                       .build();
  }

  void deleteAirline(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    log.info("Received delete airline command for id {}", airlineId);
    airlineRepository.deleteById(airlineId);
  }
}
