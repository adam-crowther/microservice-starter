package com.acroteq.ticketing.approval.service.domain.entity.airline;

import static com.acroteq.precondition.Precondition.checkPrecondition;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.AirlineId;
import com.acroteq.ticketing.approval.service.domain.exception.AirlineValidationException;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Airline extends ReplicatedEntity<AirlineId> {

  @NonNull
  private final String code;
  @NonNull
  private final String name;

  private final boolean active;
  @NonNull
  private final List<Flight> flights;

  public void validate() {
    checkPrecondition(isNotBlank(name), AirlineValidationException::new, "userName");
  }

  @SuppressWarnings({ "PublicInnerClass" })
  public abstract static class AirlineBuilder<C extends Airline, B extends AirlineBuilder<C, B>>
      extends ReplicatedEntityBuilder<AirlineId, C, B> {

    public B flights(@Nullable final List<Flight> flights) {
      this.flights = Optional.ofNullable(flights)
                             .map(ImmutableList::copyOf)
                             .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
