package com.acroteq.ticketing.kafka.producer.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerConfig {

  @NotNull String acks;
  @NotNull Integer batchSize;
  @NotNull Integer batchSizeBoostFactor;
  @NotNull Integer lingerMs;
  @NotNull Integer requestTimeoutMs;
  @NotNull Integer retryCount;

  @NotNull KafkaSerialisationConfig serialisation;
}