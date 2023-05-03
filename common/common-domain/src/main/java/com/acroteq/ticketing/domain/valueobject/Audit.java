package com.acroteq.ticketing.domain.valueobject;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Audit {

  Instant createdTimestamp;
  Instant lastModifiedTimestamp;
}
