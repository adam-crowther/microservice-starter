package com.acroteq.ticketing.order.service.domain.dto.message;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class AirlineApprovalResponseDto {

  @NotNull
  private final Long id;
  @NotNull
  private final UUID sagaId;
  @NotNull
  private final Long orderId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final Instant createdDateTime;
  @NotNull
  private final OrderApprovalStatus orderApprovalStatus;
  @NotNull
  @Builder.Default
  private final ValidationResult result = pass();
}
