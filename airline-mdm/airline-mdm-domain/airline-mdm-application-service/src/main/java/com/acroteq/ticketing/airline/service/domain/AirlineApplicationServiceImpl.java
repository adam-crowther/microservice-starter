package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.mapper.AirlineCreatedEventToResponseDtoMapper;
import com.acroteq.ticketing.airline.service.domain.mapper.AirlineDomainToDtoMapper;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.AirlineApplicationService;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineCreatedEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineDeletedEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineUpdatedEventMessagePublisher;
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

  private final AirlineCreatedEventMessagePublisher createEventPublisher;
  private final AirlineUpdatedEventMessagePublisher updateEventPublisher;
  private final AirlineDeletedEventMessagePublisher deleteEventPublisher;
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
    final AirlineCreatedEvent airlineEvent = commandProcessor.createAirline(createAirlineCommandDto);
    createEventPublisher.publish(airlineEvent);
    return eventDtoMapper.convertEventToDto(airlineEvent);
  }

  @Override
  @Transactional
  public void updateAirline(final UpdateAirlineCommandDto updateAirlineCommandDto) {
    final AirlineUpdatedEvent airlineEvent = commandProcessor.updateAirline(updateAirlineCommandDto);
    updateEventPublisher.publish(airlineEvent);
  }

  @Override
  @Transactional
  public void deleteAirline(final Long airlineId) {
    final AirlineDeletedEvent airlineEvent = commandProcessor.deleteAirline(airlineId);
    deleteEventPublisher.publish(airlineEvent);
  }
}
