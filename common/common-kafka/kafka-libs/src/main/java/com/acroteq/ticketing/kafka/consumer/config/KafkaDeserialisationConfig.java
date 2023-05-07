package com.acroteq.ticketing.kafka.consumer.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer.deserialisation")
public class KafkaDeserialisationConfig {

  @NotNull String keyDeserializerClass;
  @NotNull String valueDeserializerClass;
  @NotNull String specificAvroReader;
}
