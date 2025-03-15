package com.acroteq.ticketing.order.service.domain.dto.message;

import com.acroteq.application.dto.AuditedSagaDto;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AirlineApprovalApprovedResponseDto extends AuditedSagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long airlineId;
}
