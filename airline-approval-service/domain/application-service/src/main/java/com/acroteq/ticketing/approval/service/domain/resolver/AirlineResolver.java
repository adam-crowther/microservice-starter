package com.acroteq.ticketing.approval.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.AirlineRepository;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AirlineResolver implements Resolver<AirlineId, Airline> {

  private final AirlineRepository airlineRepository;

  @NonNull
  @Override
  public Airline resolve(@NonNull final Long id) {
    final AirlineId airlineId = AirlineId.of(id);
    return resolve(airlineId);
  }

  @NonNull
  @Override
  public Airline resolve(@NonNull final AirlineId id) {
    return airlineRepository.findById(id)
                            .orElseThrow(() -> new AirlineNotFoundException(id));
  }

  @NonNull
  @Override
  public Airline resolve(@NonNull final String code) {
    return airlineRepository.findByCode(code)
                            .orElseThrow(() -> new AirlineNotFoundException(code));
  }

  @Named("optional")
  @Nullable
  @Override
  public Airline resolveOptional(@Nullable final Long id) {
    return Optional.ofNullable(id)
                   .map(AirlineId::of)
                   .flatMap(airlineRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Airline resolveOptional(@Nullable final AirlineId id) {
    return Optional.ofNullable(id)
                   .flatMap(airlineRepository::findById)
                   .orElse(null);
  }

  @Named("optional")
  @Nullable
  @Override
  public Airline resolveOptional(@Nullable final String code) {
    return Optional.ofNullable(code)
                   .flatMap(airlineRepository::findByCode)
                   .orElse(null);
  }
}
