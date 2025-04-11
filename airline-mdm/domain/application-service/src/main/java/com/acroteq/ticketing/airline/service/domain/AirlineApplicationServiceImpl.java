package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.AirlineApplicationService;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class AirlineApplicationServiceImpl implements AirlineApplicationService {

  private final AirlineCommandProcessor commandProcessor;
  private final AirlineRepository repository;
  private final AirlineEventMessagePublisher eventPublisher;

  @Override
  @Transactional(readOnly = true)
  public List<Airline> loadAllAirlines() {
    return repository.loadAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Airline> loadAirline(final String code) {
    return repository.findByCode(code);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<AirlineId> fetchAirlineId(final String code) {
    return repository.findIdByCode(code);
  }

  @Override
  @Transactional
  public Airline createAirline(final Airline airline) {
    final Airline savedAirline = commandProcessor.createAirline(airline);
    final AirlineEvent airlineEvent = AirlineEvent.builder()
                                                  .airline(savedAirline)
                                                  .build();
    eventPublisher.publish(airlineEvent);
    return savedAirline;
  }

  @Override
  @Transactional
  public void updateAirline(final Airline airline) {
    final Airline savedAirline = commandProcessor.updateAirline(airline);
    final AirlineEvent airlineEvent = AirlineEvent.builder()
                                                  .airline(savedAirline)
                                                  .build();
    eventPublisher.publish(airlineEvent);
  }

  @Override
  @Transactional
  public void deleteAirline(final String code) {
    commandProcessor.deleteAirline(code);
    eventPublisher.publishDelete(code);
  }
}
