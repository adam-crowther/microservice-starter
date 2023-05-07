package com.acroteq.ticketing.kafka.producer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaSerialisationConfig {

  @NotNull String keySerializerClass;
  @NotNull String valueSerializerClass;
  @NotNull String compressionType;
}