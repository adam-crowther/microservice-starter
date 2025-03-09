package com.acroteq.ticketing.airline.service.domain.entity;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.ticketing.airline.service.domain.exception.AirlineValidationException;
import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Airline extends AggregateRoot<AirlineId> {

  private final String name;

  private final boolean active;

  private final ImmutableList<Flight> flights;

  public void validate() {
    checkPrecondition(isNotBlank(name), AirlineValidationException::new, "userName");
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineBuilder<C extends Airline, B extends AirlineBuilder<C, B>>
      extends AggregateRootBuilder<AirlineId, C, B> {

    public B flights(@Nullable final List<Flight> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
