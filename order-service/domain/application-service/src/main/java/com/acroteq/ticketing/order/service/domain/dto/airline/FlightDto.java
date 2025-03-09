package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class FlightDto extends ReplicatedEntityDto {

  @NotNull
  private final String flightNumber;
  @NotNull
  private final CashValue price;
}
