package com.acroteq.ticketing.kafka.consumer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaPollingConfig {

  @NotNull Integer maxPollIntervalMs;
  @NotNull Long pollTimeoutMs;
  @NotNull Integer maxPollRecords;
  @NotNull Integer maxPartitionFetchBytesDefault;
  @NotNull Integer maxPartitionFetchBytesBoostFactor;
}
