package com.acroteq.ticketing.approval.service.domain.dto;

import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class OrderItemDto implements Dto {

  @NotNull
  public Long flightId;
  public int quantity;

  public Long getId() {
    return flightId;
  }
}
