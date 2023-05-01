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
@ConfigurationProperties(prefix = "kafka.consumer.group-management")
public class KafkaGroupManagementConfig {

  @NotNull
  private final Integer sessionTimeoutMs;
  @NotNull
  private final Integer heartbeatIntervalMs;
}
