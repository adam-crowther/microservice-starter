package com.acroteq.ticketing.kafka.config;

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
@ConfigurationProperties(prefix = "kafka-config")
@EnableKafka
public class KafkaConfigData {

  private final String bootstrapServers;
  private final String schemaRegistryUrlKey;
  private final String schemaRegistryUrl;
  private final Integer numOfPartitions;
  private final Short replicationFactor;
}
