package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.mapper.AirlineDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventMessageListenerImpl implements AirlineEventMessageListener {

  private final AirlineRepository airlineRepository;
  private final AirlineDtoToDomainMapper airlineDtoToDomainMapper;

  @Transactional
  @Override
  public void airlineCreated(final AirlineDto airlineDto) {
    log.info("Creating Airline: {}", airlineDto.getId());
    final Airline airline = airlineDtoToDomainMapper.convertDtoToDomain(airlineDto);
    airlineRepository.save(airline);
  }

  @Transactional
  @Override
  public void airlineUpdated(final AirlineDto airlineDto) {
    log.info("Updating Airline: {}", airlineDto.getId());
    final Airline airline = airlineDtoToDomainMapper.convertDtoToDomain(airlineDto);
    airlineRepository.save(airline);
  }

  @Transactional
  @Override
  public void airlineDeleted(final Long id) {
    log.info("Deleting Airline: {}", id);
    final AirlineId airlineId = AirlineId.of(id);
    airlineRepository.delete(airlineId);
  }
}
