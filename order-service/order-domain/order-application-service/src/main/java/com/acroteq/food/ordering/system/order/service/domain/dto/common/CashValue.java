package com.acroteq.food.ordering.system.order.service.domain.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class CashValue {

  @NotNull BigDecimal amount;
  @NotNull UUID currencyId;
}
