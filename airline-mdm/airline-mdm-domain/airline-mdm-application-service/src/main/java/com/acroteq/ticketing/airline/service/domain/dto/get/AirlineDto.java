package com.acroteq.ticketing.airline.service.domain.dto.get;

import com.acroteq.ticketing.application.dto.AuditedEntityDto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder(toBuilder = true)
public class AirlineDto extends AuditedEntityDto {

  @NotNull
  private String name;
  @NotNull
  private boolean active;
  @NotNull
  private ImmutableList<FlightDto> flights;

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineDtoBuilder<C extends AirlineDto, B extends AirlineDtoBuilder<C, B>>
      extends AuditedEntityDtoBuilder<C, B> {

    public B flights(@Nullable final List<FlightDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}

