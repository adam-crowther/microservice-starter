package com.acroteq.ticketing.airline.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFlightCommandDto implements Dto {

  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
