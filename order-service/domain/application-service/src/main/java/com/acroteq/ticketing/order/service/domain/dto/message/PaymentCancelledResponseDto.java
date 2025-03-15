package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.domain.validation.ValidationResult.pass;

import com.acroteq.application.dto.AuditedSagaDto;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PaymentCancelledResponseDto extends AuditedSagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long paymentId;
  @NotNull
  private final Long customerId;
  @NotNull
  private final CashValue value;
  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();
}
