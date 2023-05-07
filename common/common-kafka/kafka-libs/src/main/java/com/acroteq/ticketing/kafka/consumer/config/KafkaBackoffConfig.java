package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer.backoff")
public class KafkaBackoffConfig {

  @NotNull Integer maxRetries;
  @NotNull Integer initialIntervalInSeconds;
  @NotNull Double multiplier;
  @NotNull Integer maxIntervalInSeconds;

  public long getInitialIntervalInMilliseconds() {
    return initialIntervalInSeconds * 1000;
  }

  public long getMaxIntervalInMilliSeconds() {
    return maxIntervalInSeconds * 1000;
  }
}
