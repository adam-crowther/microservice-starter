package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineCreatedDto;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDeletedDto;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineUpdatedDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.mapper.airline.AirlineCreatedDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.mapper.airline.AirlineDeletedDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.mapper.airline.AirlineUpdatedDtoToDomainMapper;
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
  private final AirlineCreatedDtoToDomainMapper createdMapper;
  private final AirlineUpdatedDtoToDomainMapper updatedMapper;
  private final AirlineDeletedDtoToDomainMapper deletedMapper;

  @Transactional
  @Override
  public void airlineCreated(final AirlineCreatedDto dto) {
    log.info("Creating Airline: {}", dto.getId());
    final Airline airline = createdMapper.convertDtoToDomain(dto);
    airlineRepository.insert(airline);
  }

  @Transactional
  @Override
  public void airlineUpdated(final AirlineUpdatedDto dto) {
    log.info("Updating Airline: {}", dto.getId());
    final Airline airline = updatedMapper.convertDtoToDomain(dto);
    airlineRepository.update(airline);
  }

  @Transactional
  @Override
  public void airlineDeleted(final AirlineDeletedDto dto) {
    log.info("Deleting Airline: {}", dto.getId());
    final AirlineId airlineId = deletedMapper.convertDtoToDomain(dto);
    airlineRepository.deleteById(airlineId);
  }
}
