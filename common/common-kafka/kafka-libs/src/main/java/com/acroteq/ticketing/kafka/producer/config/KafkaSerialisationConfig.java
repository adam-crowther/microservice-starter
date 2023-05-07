package com.acroteq.ticketing.kafka.producer.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.producer.serialisation")
public class KafkaSerialisationConfig {

  @NotNull String keySerializerClass;
  @NotNull String valueSerializerClass;
  @NotNull String compressionType;
}