package com.acroteq.ticketing.payment.service.domain.dto.payment;

import com.acroteq.ticketing.application.dto.SagaDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PaymentCancelRequestDto extends SagaDto {

  @NotNull
  private final Long orderId;
}
