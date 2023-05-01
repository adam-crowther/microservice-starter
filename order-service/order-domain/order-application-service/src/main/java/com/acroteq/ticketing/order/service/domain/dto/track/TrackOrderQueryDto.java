package com.acroteq.ticketing.order.service.domain.dto.track;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class TrackOrderQueryDto implements Dto {

  @NotNull
  private final UUID trackingId;
}
