package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class FlightDto extends ReplicatedEntityDto {

  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
