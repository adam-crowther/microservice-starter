package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.application.dto.Dto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class AirlineUpdatedDto implements Dto {

  @NotNull
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private boolean active;
  @NotNull
  private ImmutableList<FlightDto> flights;

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineUpdatedDtoBuilder {

    public AirlineUpdatedDtoBuilder flights(@Nullable final List<FlightDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this;
    }
  }
}

