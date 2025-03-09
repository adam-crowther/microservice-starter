package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateFlightCommandDto implements DataTransferObject {

  @NotNull
  private final String flightNumber;
  @NotNull
  private final CashValue price;

  private final boolean available;
}
