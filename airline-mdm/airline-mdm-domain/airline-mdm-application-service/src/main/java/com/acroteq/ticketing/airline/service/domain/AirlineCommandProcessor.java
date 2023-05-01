package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
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

  AirlineCreatedEvent createAirline(final CreateAirlineCommandDto createAirlineCommandDto) {
    log.info("Received create airline command");
    final Airline airline = createAirlineMapper.convertDtoToDomain(createAirlineCommandDto);
    airlineDomainService.validateAirline(airline);
    final Airline savedAirline = airlineRepository.save(airline);

    return AirlineCreatedEvent.builder()
                              .airline(savedAirline)
                              .build();
  }

  AirlineUpdatedEvent updateAirline(final UpdateAirlineCommandDto updateAirlineCommandDto) {
    log.info("Received update airline command for id {}", updateAirlineCommandDto.getId());
    final Airline airline = updateAirlineMapper.convertDtoToDomain(updateAirlineCommandDto);
    airlineDomainService.validateAirline(airline);
    final Airline savedAirline = airlineRepository.save(airline);

    return AirlineUpdatedEvent.builder()
                              .airline(savedAirline)
                              .build();
  }

  AirlineDeletedEvent deleteAirline(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    log.info("Received delete airline command for id {}", airlineId);
    airlineRepository.deleteById(airlineId);

    return AirlineDeletedEvent.builder()
                              .airlineId(airlineId)
                              .build();
  }
}
