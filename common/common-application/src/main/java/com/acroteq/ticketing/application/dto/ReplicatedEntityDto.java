package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
public class ReplicatedEntityDto extends EntityDto {

  @NotNull
  private final EventIdDto eventId;
}
