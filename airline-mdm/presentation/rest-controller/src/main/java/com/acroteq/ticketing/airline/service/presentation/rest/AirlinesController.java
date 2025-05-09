package com.acroteq.ticketing.airline.service.presentation.rest;

import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.AirlineApplicationService;
import com.acroteq.ticketing.airline.service.presentation.api.AirlinesApi;
import com.acroteq.ticketing.airline.service.presentation.mapper.AirlineMapper;
import com.acroteq.ticketing.airline.service.presentation.mapper.AuditedEntityResponseMapper;
import com.acroteq.ticketing.airline.service.presentation.model.Airline;
import com.acroteq.ticketing.airline.service.presentation.model.AuditedEntityResponse;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirline;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = { "${airline-service.permit-cross-origin-from}" })
public class AirlinesController implements AirlinesApi {

  private final AirlineMapper airlineMapper;
  private final AuditedEntityResponseMapper auditedResponseMapper;
  private final AirlineApplicationService airlineApplicationService;

  @Override
  public ResponseEntity<List<Airline>> listAirlines(final Integer limit) {
    final List<Airline> airlines = airlineApplicationService.loadAllAirlines()
                                                            .stream()
                                                            .map(airlineMapper::convert)
                                                            .toList();
    return ResponseEntity.ok(airlines);
  }

  @Override
  public ResponseEntity<Airline> getAirlineByCode(final String airlineCode) {
    final Airline airline = airlineApplicationService.loadAirline(airlineCode)
                                                     .map(airlineMapper::convert)
                                                     .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    return ResponseEntity.ok(airline);
  }

  @Override
  public ResponseEntity<AuditedEntityResponse> createAirline(final CreateAirline command) {
    log.info("Creating airline {}", command.getName());

    final com.acroteq.ticketing.airline.service.domain.entity.Airline airline = airlineMapper.convert(command);
    final com.acroteq.ticketing.airline.service.domain.entity.Airline savedAirline =
        airlineApplicationService.createAirline(airline);
    final AuditedEntityResponse response = auditedResponseMapper.convert(savedAirline);

    log.info("Airline created with id {}", savedAirline);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> updateAirlineByCode(final String airlineCode, final UpdateAirline command) {
    log.info("Updating airline with code {}", command.getCode());
    final com.acroteq.ticketing.airline.service.domain.entity.Airline airline = //
        airlineApplicationService.loadAirline(airlineCode)
                                 .map(airlineMapper.convertToExisting(command))
                                 .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
    airlineApplicationService.updateAirline(airline);

    log.info("Airline with code {} updated", command.getCode());
    return ResponseEntity.ok()
                         .build();
  }

  @Override
  public ResponseEntity<Void> deleteAirlineByCode(final String airlineCode) {
    log.info("Deleting airline with code {}", airlineCode);

    airlineApplicationService.deleteAirline(airlineCode);

    log.info("Airline with code {} deleted", airlineCode);
    return ResponseEntity.ok()
                         .build();
  }
}
