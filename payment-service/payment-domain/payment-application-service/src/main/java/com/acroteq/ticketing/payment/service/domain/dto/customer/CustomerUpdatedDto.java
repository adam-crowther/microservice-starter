package com.acroteq.ticketing.payment.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerUpdatedDto implements Dto {

  @NotNull
  private Long id;
  @NotNull
  private CashValue creditLimit;
}
