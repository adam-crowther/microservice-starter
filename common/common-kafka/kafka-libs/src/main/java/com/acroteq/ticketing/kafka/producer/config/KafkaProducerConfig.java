package com.acroteq.ticketing.kafka.producer.config;

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
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerConfig {

  @NotNull
  private final String acks;
  @NotNull
  private final Integer batchSize;
  @NotNull
  private final Integer batchSizeBoostFactor;
  @NotNull
  private final Integer lingerMs;
  @NotNull
  private final Integer requestTimeoutMs;
  @NotNull
  private final Integer retryCount;

  @NotNull
  private final KafkaSerialisationConfig serialisation;
}