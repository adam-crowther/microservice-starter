package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.mapper.AirlineDtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
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
    airlineRepository.deleteById(airlineId);
  }
}
