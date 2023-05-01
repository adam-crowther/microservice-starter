package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FlightDto implements Dto {

  private Long id;
  private String flightNumber;
  private CashValue price;
}
