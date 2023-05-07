package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer.group-management")
public class KafkaGroupManagementConfig {

  @NotNull Integer sessionTimeoutMs;
  @NotNull Integer heartbeatIntervalMs;
}
