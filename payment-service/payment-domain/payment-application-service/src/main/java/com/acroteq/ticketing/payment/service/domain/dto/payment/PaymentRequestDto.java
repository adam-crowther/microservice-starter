package com.acroteq.ticketing.payment.service.domain.dto.payment;

import com.acroteq.ticketing.application.dto.SagaDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaymentRequestDto extends SagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long orderVersion;
  @NotNull
  private final Long customerId;
  @NotNull
  private final CashValue value;
}
