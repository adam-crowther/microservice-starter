package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder(toBuilder = true)
public class AirlineEventDto extends ReplicatedEntityDto {

  @NotNull
  private String name;
  @NotNull
  private ImmutableList<FlightDto> flights;
  @NotNull
  private boolean active;

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineEventDtoBuilder<C extends AirlineEventDto, B extends AirlineEventDtoBuilder<C, B>>
      extends ReplicatedEntityDtoBuilder<C, B> {

    public B flights(@Nullable final List<FlightDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
