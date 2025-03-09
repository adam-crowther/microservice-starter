package com.acroteq.domain.valueobject;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Audit {

  Instant createdTimestamp;
  Instant lastModifiedTimestamp;

  public static Audit now() {
    final Instant now = Instant.now();
    return Audit.builder()
                .createdTimestamp(now)
                .lastModifiedTimestamp(now)
                .build();
  }
}
