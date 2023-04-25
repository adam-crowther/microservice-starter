package com.acroteq.ticketing.airline.service.domain.dto.update;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateFlightCommandDto {

  @NotNull
  private String id;
  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
