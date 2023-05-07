package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer.dead-letter")
public class KafkaDeadLetterConfig {

  @NotNull String suffix;
}
