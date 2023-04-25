package com.acroteq.ticketing.approval.service.domain.dto;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class OrderItemDto {

  @NotNull
  public Long flightId;
  public int quantity;
}
