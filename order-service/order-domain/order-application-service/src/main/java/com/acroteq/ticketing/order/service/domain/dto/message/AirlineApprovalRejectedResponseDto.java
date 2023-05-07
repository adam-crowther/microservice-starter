package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.application.dto.AuditedSagaDto;
import com.acroteq.ticketing.domain.validation.ValidationResult;
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
public class AirlineApprovalRejectedResponseDto extends AuditedSagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long orderVersion;
  @NotNull
  private final Long airlineId;
  @NotNull
  @Builder.Default
  private final ValidationResult result = pass();
}