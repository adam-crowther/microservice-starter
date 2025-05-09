package com.acroteq.ticketing.approval.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.approval.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.FlightRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class FlightResolver implements Resolver<FlightId, Flight> {

  private final FlightRepository flightRepository;

  @NonNull
  @Override
  public Flight resolve(@NonNull final Long id) {
    final FlightId flightId = FlightId.of(id);
    return resolve(flightId);
  }

  @NonNull
  @Override
  public Flight resolve(@NonNull final FlightId id) {
    return flightRepository.findById(id)
                           .orElseThrow(() -> new FlightNotFoundException(id));
  }

  @NonNull
  @Override
  public Flight resolve(@NonNull final String code) {
    return flightRepository.findByCode(code)
                           .orElseThrow(() -> new FlightNotFoundException(code));
  }

  @Named("optional")
  @Nullable
  @Override
  public Flight resolveOptional(@Nullable final Long id) {
    return Optional.ofNullable(id)
                   .map(FlightId::of)
                   .flatMap(flightRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Flight resolveOptional(@Nullable final FlightId id) {
    return Optional.ofNullable(id)
                   .flatMap(flightRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Flight resolveOptional(@Nullable final String code) {
    return Optional.ofNullable(code)
                   .flatMap(flightRepository::findByCode)
                   .orElse(null);
  }
}
