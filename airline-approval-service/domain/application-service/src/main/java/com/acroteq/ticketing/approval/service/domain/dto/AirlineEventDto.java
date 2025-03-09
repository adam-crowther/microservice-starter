package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AirlineEventDto extends ReplicatedEntityDto {

  @NotNull
  private final String name;
  @NotNull
  private final boolean active;
  @NotNull
  private final ImmutableList<FlightDto> flights;

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

