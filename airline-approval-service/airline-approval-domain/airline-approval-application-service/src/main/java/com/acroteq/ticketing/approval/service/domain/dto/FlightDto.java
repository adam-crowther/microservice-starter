package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlightDto {

  @NotNull
  private String id;
  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
