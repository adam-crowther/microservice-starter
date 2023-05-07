package com.acroteq.ticketing.kafka.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@Value
@ConfigurationProperties(prefix = "kafka")
@EnableKafka
public class KafkaConfig {

  @NotNull String bootstrapServers;
  @NotNull String schemaRegistryUrl;
  @NotNull Integer numOfPartitions;
  @NotNull Short replicationFactor;
}
