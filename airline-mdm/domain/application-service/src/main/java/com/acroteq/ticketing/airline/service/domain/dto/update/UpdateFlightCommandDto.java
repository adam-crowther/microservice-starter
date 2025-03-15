package com.acroteq.ticketing.airline.service.domain.dto.update;

import com.acroteq.application.dto.EntityDto;
import com.acroteq.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UpdateFlightCommandDto extends EntityDto {

  @NotNull
  private final String flightNumber;
  @NotNull
  private final CashValue price;

  private final boolean available;
}
