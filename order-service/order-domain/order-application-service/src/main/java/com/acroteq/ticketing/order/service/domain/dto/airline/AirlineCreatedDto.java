package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.application.dto.Dto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class AirlineCreatedDto implements Dto {

  private Long id;
  private String name;
  private ImmutableList<FlightDto> flights;
  private boolean active;

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineCreatedDtoBuilder {

    public AirlineCreatedDtoBuilder flights(@Nullable final List<FlightDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this;
    }
  }
}
