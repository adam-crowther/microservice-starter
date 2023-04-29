package com.acroteq.ticketing.airline.service.presentation.rest;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineResponseDto;
import com.acroteq.ticketing.airline.service.domain.dto.get.AirlineDto;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.AirlineApplicationService;
import com.acroteq.ticketing.airline.service.presentation.api.AirlinesApi;
import com.acroteq.ticketing.airline.service.presentation.exception.MismatchedAirlineIdException;
import com.acroteq.ticketing.airline.service.presentation.mapper.AirlineDtoToApiMapper;
import com.acroteq.ticketing.airline.service.presentation.mapper.CreateAirlineCommandApiToDtoMapper;
import com.acroteq.ticketing.airline.service.presentation.mapper.CreateAirlineResponseDtoToApiMapper;
import com.acroteq.ticketing.airline.service.presentation.mapper.UpdateAirlineCommandApiToDtoMapper;
import com.acroteq.ticketing.airline.service.presentation.model.Airline;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineCommand;
import com.acroteq.ticketing.airline.service.presentation.model.CreateAirlineResponse;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateAirlineCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:${airline-service.swagger-port}")
public class AirlinesController implements AirlinesApi {

  private final AirlineDtoToApiMapper createMapper;
  private final CreateAirlineCommandApiToDtoMapper createCommandMapper;
  private final CreateAirlineResponseDtoToApiMapper createResponseMapper;
  private final UpdateAirlineCommandApiToDtoMapper updateCommandMapper;
  private final AirlineApplicationService airlineApplicationService;

  @Override
  public ResponseEntity<Airline> getAirlineById(final Long id) {
    final AirlineDto responseDto = airlineApplicationService.getAirline(id);
    final Airline response = createMapper.convertDtoToApi(responseDto);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CreateAirlineResponse> createAirline(final CreateAirlineCommand command) {
    log.info("Creating airline {}", command.getName());

    final CreateAirlineCommandDto commandDto = createCommandMapper.convertApiToDto(command);
    final CreateAirlineResponseDto responseDto = airlineApplicationService.createAirline(commandDto);
    final CreateAirlineResponse response = createResponseMapper.convertDtoToApi(responseDto);

    log.info("Airline created with id {}", response.getAirlineId());
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> updateAirlineById(final Long id, final UpdateAirlineCommand command) {
    if (!id.equals(command.getId())) {
      throw new MismatchedAirlineIdException(id, command.getId());
    }

    log.info("Updating airline with id {}", command.getId());

    final UpdateAirlineCommandDto commandDto = updateCommandMapper.convertApiToDto(command);
    airlineApplicationService.updateAirline(commandDto);

    log.info("Airline with id {} updated", command.getId());
    return ResponseEntity.ok()
                         .build();
  }

  @Override
  public ResponseEntity<Void> deleteAirlineById(final Long airlineId) {
    log.info("Deleting airline with id {}", airlineId);

    airlineApplicationService.deleteAirline(airlineId);

    log.info("Airline with id {} deleted", airlineId);
    return ResponseEntity.ok()
                         .build();
  }
}
