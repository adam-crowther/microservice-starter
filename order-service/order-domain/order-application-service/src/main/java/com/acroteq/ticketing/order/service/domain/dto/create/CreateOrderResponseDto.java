package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.AuditDto;
import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateOrderResponseDto implements DataTransferObject {

  @NotNull
  private final UUID trackingId;
  @NotNull
  private final AuditDto audit;
  @NotNull
  private final OrderStatus status;
  @NotNull
  private final String message;
}
