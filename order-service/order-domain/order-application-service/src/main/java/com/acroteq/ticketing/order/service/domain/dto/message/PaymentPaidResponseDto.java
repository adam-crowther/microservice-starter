package com.acroteq.ticketing.order.service.domain.dto.message;

import com.acroteq.ticketing.application.dto.AuditedSagaDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PaymentPaidResponseDto extends AuditedSagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long paymentId;
  @NotNull
  private final Long customerId;
  @NotNull
  private final CashValue value;
}
