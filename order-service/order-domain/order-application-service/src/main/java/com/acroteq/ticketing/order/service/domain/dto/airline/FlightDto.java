package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FlightDto {

  private Long id;
  private String flightNumber;
  private CashValue price;
}
