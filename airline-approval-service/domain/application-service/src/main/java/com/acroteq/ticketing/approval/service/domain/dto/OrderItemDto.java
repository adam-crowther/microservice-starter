package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.application.dto.DataTransferObject;
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
  public final Long flightId;
  public final int quantity;

  public final Long getId() {
    return flightId;
  }
}
