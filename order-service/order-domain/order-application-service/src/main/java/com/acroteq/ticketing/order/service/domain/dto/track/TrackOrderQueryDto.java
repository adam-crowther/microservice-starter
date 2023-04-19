package com.acroteq.ticketing.order.service.domain.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class TrackOrderQueryDto {

  @NotNull
  private final UUID trackingId;
}
