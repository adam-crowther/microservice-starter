package com.acroteq.ticketing.airline.service.domain.dto.update;

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
public class UpdateAirlineCommandDto implements Dto {

  @NotNull
  private String id;
  @NotNull
  private String name;
  @NotNull
  private boolean active;
  @NotNull
  private ImmutableList<UpdateFlightCommandDto> flights;

  @SuppressWarnings("PublicInnerClass")
  public static class UpdateAirlineCommandDtoBuilder {

    public UpdateAirlineCommandDtoBuilder flights(@Nullable final List<UpdateFlightCommandDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this;
    }
  }
}
