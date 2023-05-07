package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateAirlineCommandDto implements DataTransferObject {

  @NotNull
  private final String name;
  @NotNull
  private final boolean active;
  @NotNull
  private final ImmutableList<CreateFlightCommandDto> flights;


  @SuppressWarnings("PublicInnerClass")
  public static class CreateAirlineCommandDtoBuilder {

    public CreateAirlineCommandDtoBuilder flights(@Nullable final List<CreateFlightCommandDto> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this;
    }
  }
}
