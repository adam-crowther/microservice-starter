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
@ConfigurationProperties(prefix = "kafka.consumer.polling")
public class KafkaPollingConfig {

  @NotNull
  private final Integer maxPollIntervalMs;
  @NotNull
  private final Long pollTimeoutMs;
  @NotNull
  private final Integer maxPollRecords;
  @NotNull
  private final Integer maxPartitionFetchBytesDefault;
  @NotNull
  private final Integer maxPartitionFetchBytesBoostFactor;
}
