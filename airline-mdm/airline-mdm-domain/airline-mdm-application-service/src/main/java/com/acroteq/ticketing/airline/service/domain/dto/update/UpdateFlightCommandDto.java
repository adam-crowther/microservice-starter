package com.acroteq.ticketing.airline.service.domain.dto.update;

import com.acroteq.ticketing.application.dto.EntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class UpdateFlightCommandDto extends EntityDto {

  @NotNull
  private String flightNumber;
  @NotNull
  private CashValue price;

  private boolean available;
}
