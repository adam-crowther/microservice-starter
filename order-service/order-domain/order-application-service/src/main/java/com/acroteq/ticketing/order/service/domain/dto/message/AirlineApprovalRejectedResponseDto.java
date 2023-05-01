package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class AirlineApprovalRejectedResponseDto implements Dto {

  @NotNull
  private final UUID sagaId;
  @NotNull
  private final Long orderId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final Instant createdDateTime;
  @NotNull
  @Builder.Default
  private final ValidationResult result = pass();
}