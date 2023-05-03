package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFlightCommandDto implements DataTransferObject {

  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
