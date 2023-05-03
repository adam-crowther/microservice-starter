package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineEventDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.exception.AirlineEventProcessingOrderException;
import com.acroteq.ticketing.order.service.domain.mapper.airline.AirlineEventDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventMessageListenerImpl implements AirlineEventMessageListener {

  private final AirlineRepository airlineRepository;
  private final AirlineEventDtoToDomainMapper createdMapper;

  @Transactional
  @Override
  public void airlineCreatedOrUpdated(final AirlineEventDto dto) {
    log.info("Creating Airline: {}", dto.getId());
    final Airline airline = createdMapper.convertDtoToDomain(dto);
    if (!eventAlreadyProcessed(airline)) {
      airlineRepository.save(airline);
    }
  }

  private boolean eventAlreadyProcessed(final Airline newAirline) {
    final AirlineId airlineId = newAirline.getId();
    return airlineRepository.findById(airlineId)
                            .map(alreadyProcessed(newAirline))
                            .orElse(false);
  }

  private Function<Airline, Boolean> alreadyProcessed(final Airline newAirline) {
    return existingAirline -> alreadyProcessed(existingAirline, newAirline);
  }

  private Boolean alreadyProcessed(final Airline existingAirline, final Airline newAirline) {

    if (newAirline.isFromAnEarlierEventThan(existingAirline)) {
      throw new AirlineEventProcessingOrderException(newAirline.getId());
    }

    final boolean isAlreadyProcessed = newAirline.isFromTheSameEventAs(existingAirline);
    if (isAlreadyProcessed) {
      log.debug("AirlineUpdatedEvent for Airline {} with eventId {} was already processed.",
                newAirline.getId(),
                newAirline.getEventId());
    }

    return isAlreadyProcessed;
  }

  @Transactional
  @Override
  public void airlineDeleted(final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    log.info("Deleting Airline: {}", airlineId);
    airlineRepository.deleteById(airlineId);
  }
}
