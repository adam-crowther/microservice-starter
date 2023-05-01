package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "kafka.consumer.backoff")
public class KafkaBackoffConfig {

  @NotNull
  private final Integer maxRetries;
  @NotNull
  private final Integer initialIntervalInSeconds;
  @NotNull
  private final Double multiplier;
  @NotNull
  private final Integer maxIntervalInSeconds;

  public final long getInitialIntervalInMilliseconds() {
    return initialIntervalInSeconds * 1000;
  }

  public final long getMaxIntervalInMilliSeconds() {
    return maxIntervalInSeconds * 1000;
  }
}
