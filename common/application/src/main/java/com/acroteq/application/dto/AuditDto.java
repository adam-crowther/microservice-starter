package com.acroteq.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public final class AuditDto {

  private final Instant createdTimestamp;
  private final Instant lastModifiedTimestamp;
}
