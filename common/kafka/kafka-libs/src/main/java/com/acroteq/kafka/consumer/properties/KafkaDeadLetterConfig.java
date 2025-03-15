package com.acroteq.kafka.consumer.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class KafkaDeadLetterConfig {

  @NotNull
  String suffix;
}
