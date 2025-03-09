package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemDto implements DataTransferObject {

  @NotNull
  private final Long flightId;
  @NotNull
  private final Integer quantity;
}
