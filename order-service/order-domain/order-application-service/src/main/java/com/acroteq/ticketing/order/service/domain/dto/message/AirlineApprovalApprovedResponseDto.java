package com.acroteq.ticketing.order.service.domain.dto.message;

import com.acroteq.ticketing.application.dto.AuditedSagaDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AirlineApprovalApprovedResponseDto extends AuditedSagaDto {

  @NotNull
  private final Long orderId;
  @NotNull
  private final Long airlineId;
}
