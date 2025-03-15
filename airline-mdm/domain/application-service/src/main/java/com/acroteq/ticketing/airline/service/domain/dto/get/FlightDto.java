package com.acroteq.ticketing.airline.service.domain.dto.get;

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
public class FlightDto extends EntityDto {

  @NotNull
  private final String flightNumber;
  @NotNull
  private final CashValue price;

  private final boolean available;
}
