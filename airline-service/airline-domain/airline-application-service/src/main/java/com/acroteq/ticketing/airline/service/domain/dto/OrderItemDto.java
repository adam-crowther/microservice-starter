package com.acroteq.ticketing.airline.service.domain.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class OrderItemDto {

  public Long flightId;
  public int quantity;
}
