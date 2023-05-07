package com.acroteq.ticketing.order.service.domain.dto.track;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrackOrderQueryDto implements DataTransferObject {

  @NotNull
  private final UUID trackingId;
}
