package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.mapper.AirlineCreatedEventToResponseDtoMapper;
import com.acroteq.ticketing.airline.service.domain.mapper.AirlineDomainToDtoMapper;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.AirlineApplicationService;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class AirlineApplicationServiceImpl implements AirlineApplicationService {

  private final AirlineCommandProcessor commandProcessor;
  private final AirlineRepository repository;

  private final AirlineEventMessagePublisher eventPublisher;
  private final AirlineDomainToDtoMapper domainDtoMapper;
  private final AirlineCreatedEventToResponseDtoMapper eventDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public AirlineDto getAirline(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    final Airline airline = repository.findById(airlineId)
                                      .orElseThrow(() -> new AirlineNotFoundException(airlineId));
    return domainDtoMapper.convertDomainToDto(airline);
  }

  @Override
  @Transactional
  public CreateAirlineResponseDto createAirline(final CreateAirlineCommandDto createAirlineCommandDto) {
    final AirlineEvent airlineEvent = commandProcessor.createAirline(createAirlineCommandDto);
    eventPublisher.publish(airlineEvent);
    return eventDtoMapper.convertCreatedEventToResponseDto(airlineEvent);
  }

  @Override
  @Transactional
  public void updateAirline(final UpdateAirlineCommandDto updateAirlineCommandDto) {
    final AirlineEvent airlineEvent = commandProcessor.updateAirline(updateAirlineCommandDto);
    eventPublisher.publish(airlineEvent);
  }

  @Override
  @Transactional
  public void deleteAirline(final Long airlineId) {
    final AirlineEvent airlineEvent = commandProcessor.deleteAirline(airlineId);
    eventPublisher.publish(airlineEvent);
  }
}
