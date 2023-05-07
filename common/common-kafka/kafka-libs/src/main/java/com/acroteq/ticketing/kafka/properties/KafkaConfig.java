package com.acroteq.ticketing.kafka.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

  @NotNull String bootstrapServers;
  @NotNull String schemaRegistryUrl;
  @NotNull Integer numOfPartitions;
  @NotNull Short replicationFactor;
}
