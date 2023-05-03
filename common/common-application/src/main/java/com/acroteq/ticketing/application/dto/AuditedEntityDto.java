package com.acroteq.ticketing.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
public class AuditedEntityDto extends EntityDto {

  @NotNull
  private final AuditDto audit;
}
