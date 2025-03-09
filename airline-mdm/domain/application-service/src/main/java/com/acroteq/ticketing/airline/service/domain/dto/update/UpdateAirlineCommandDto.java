package com.acroteq.ticketing.airline.service.domain.dto.update;

import com.acroteq.ticketing.application.dto.EntityDto;
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
public class UpdateAirlineCommandDto extends EntityDto {

  @NotNull
  private final String name;
  @NotNull
  private final boolean active;
  @NotNull
  private final ImmutableList<UpdateFlightCommandDto> flights;

  @SuppressWarnings("PublicInnerClass")
  public abstract static class UpdateAirlineCommandDtoBuilder<C extends UpdateAirlineCommandDto,
      B extends UpdateAirlineCommandDtoBuilder<C, B>>
      extends EntityDtoBuilder<C, B> {

    public B flights(@Nullable final List<UpdateFlightCommandDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
