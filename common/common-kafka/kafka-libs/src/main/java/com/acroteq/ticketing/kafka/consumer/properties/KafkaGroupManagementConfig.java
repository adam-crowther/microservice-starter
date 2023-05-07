package com.acroteq.ticketing.kafka.consumer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaGroupManagementConfig {

  @NotNull Integer sessionTimeoutMs;
  @NotNull Integer heartbeatIntervalMs;
}
