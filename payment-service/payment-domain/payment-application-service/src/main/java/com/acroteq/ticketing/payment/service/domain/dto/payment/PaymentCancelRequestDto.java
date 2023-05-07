package com.acroteq.ticketing.payment.service.domain.dto.payment;

import com.acroteq.ticketing.application.dto.SagaDto;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PaymentCancelRequestDto extends SagaDto {

  @NotNull
  private final Long orderId;
}
