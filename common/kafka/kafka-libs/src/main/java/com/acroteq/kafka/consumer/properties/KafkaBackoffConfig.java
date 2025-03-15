package com.acroteq.kafka.consumer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaBackoffConfig {

  @NotNull
  Integer maxRetries;
  @NotNull
  Integer initialIntervalInSeconds;
  @NotNull
  Double multiplier;
  @NotNull
  Integer maxIntervalInSeconds;

  public long getInitialIntervalInMilliseconds() {
    return initialIntervalInSeconds * 1000;
  }

  public long getMaxIntervalInMilliSeconds() {
    return maxIntervalInSeconds * 1000;
  }
}
