package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer.polling")
public class KafkaPollingConfig {

  @NotNull Integer maxPollIntervalMs;
  @NotNull Long pollTimeoutMs;
  @NotNull Integer maxPollRecords;
  @NotNull Integer maxPartitionFetchBytesDefault;
  @NotNull Integer maxPartitionFetchBytesBoostFactor;
}
