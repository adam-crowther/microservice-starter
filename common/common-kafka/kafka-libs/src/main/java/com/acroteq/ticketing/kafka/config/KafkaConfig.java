package com.acroteq.ticketing.kafka.config;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "kafka")
@EnableKafka
public class KafkaConfig {

  @NotNull
  private final String bootstrapServers;
  @NotNull
  private final String schemaRegistryUrl;
  @NotNull
  private final Integer numOfPartitions;
  @NotNull
  private final Short replicationFactor;
}
