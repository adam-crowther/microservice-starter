package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineCreatedDto;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineDeletedDto;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineUpdatedDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.mapper.AirlineCreatedDtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.mapper.AirlineDeletedDtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.mapper.AirlineUpdatedDtoToDomainMapper;
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
    final AirlineId airlineId = deletedMapper.convertDtoToDomain(dto);
    log.info("Deleting Airline: {}", airlineId);
    airlineRepository.deleteById(airlineId);
  }
}
