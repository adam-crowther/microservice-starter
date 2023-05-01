package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlightDto implements Dto {

  @NotNull
  private Long id;
  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
