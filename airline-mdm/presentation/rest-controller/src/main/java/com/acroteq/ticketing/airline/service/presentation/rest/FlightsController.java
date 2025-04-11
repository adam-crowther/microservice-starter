package com.acroteq.ticketing.airline.service.presentation.rest;

import com.acroteq.ticketing.airline.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.input.service.FlightApplicationService;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.FlightRepository;
import com.acroteq.ticketing.airline.service.presentation.api.FlightsApi;
import com.acroteq.ticketing.airline.service.presentation.exception.MismatchedAirlineException;
import com.acroteq.ticketing.airline.service.presentation.mapper.EntityResponseMapper;
import com.acroteq.ticketing.airline.service.presentation.mapper.FlightMapper;
import com.acroteq.ticketing.airline.service.presentation.model.CreateFlight;
import com.acroteq.ticketing.airline.service.presentation.model.EntityResponse;
import com.acroteq.ticketing.airline.service.presentation.model.Flight;
import com.acroteq.ticketing.airline.service.presentation.model.UpdateFlight;
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
public class FlightsController implements FlightsApi {

  private final FlightMapper flightMapper;
  private final EntityResponseMapper responseMapper;
  private final FlightApplicationService flightApplicationService;
  private final FlightRepository flightRepository;

  @Override
  public ResponseEntity<List<Flight>> listAirlineFlights(final String airlineCode, final Integer limit) {
    final List<Flight> flights = flightApplicationService.loadAllFlightsByAirlineCode(airlineCode)
                                                         .stream()
                                                         .map(flightMapper::convert)
                                                         .toList();

    return ResponseEntity.ok(flights);
  }

  @Override
  public ResponseEntity<Flight> getAirlineFlightByCode(final String airlineCode, final String flightCode) {
    final Flight flight = flightApplicationService.loadFlight(flightCode)
                                                  .map(flightMapper::convert)
                                                  .orElseThrow(() -> new FlightNotFoundException(flightCode));
    return ResponseEntity.ok(flight);
  }

  @Override
  public ResponseEntity<EntityResponse> createAirlineFlight(final String airlineCode, final CreateFlight command) {
    log.info("Creating flight {} in airline with code {}", command.getCode(), airlineCode);

    final com.acroteq.ticketing.airline.service.domain.entity.Flight flight = flightMapper.convert(command,
                                                                                                   airlineCode);
    final com.acroteq.ticketing.airline.service.domain.entity.Flight savedFlight =
        flightApplicationService.createFlight(airlineCode, flight);
    final EntityResponse response = responseMapper.convert(savedFlight);

    log.info("Flight created with code {} in airline with code {}", flight.getCode(), airlineCode);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> updateAirlineFlightByCode(
      final String airlineCode, final String flightCode,
      final UpdateFlight command) {
    if (!flightCode.equals(command.getCode())) {
      throw new MismatchedAirlineException(flightCode, airlineCode);
    }

    log.info("Updating flight with symbolic code {} in airline with symbolic code {}", flightCode, airlineCode);

    final com.acroteq.ticketing.airline.service.domain.entity.Flight flight = //
        flightRepository.findByCode(flightCode)
                        .map(flightMapper.convertToExisting(command))
                        .orElseThrow(() -> new FlightNotFoundException(flightCode));
    flightApplicationService.updateFlight(airlineCode, flight);

    log.info("Flight with symbolic code {} in airline with symbolic code {} updated", flightCode, airlineCode);
    return ResponseEntity.ok()
                         .build();
  }

  @Override
  public ResponseEntity<Void> deleteFlightByCode(final String airlineCode, final String flightCode) {
    log.info("Deleting flight with symbolic code {} in airline with symbolic code {}", flightCode, airlineCode);

    flightApplicationService.deleteFlight(airlineCode, flightCode);

    log.info("Flight with symbolic code {} in airline with symbolic code {} deleted", flightCode, airlineCode);
    return ResponseEntity.ok()
                         .build();

  }
}
