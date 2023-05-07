package com.acroteq.ticketing.kafka.consumer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaDeserialisationConfig {

  @NotNull String keyDeserializerClass;
  @NotNull String valueDeserializerClass;
  @NotNull String specificAvroReader;
}
