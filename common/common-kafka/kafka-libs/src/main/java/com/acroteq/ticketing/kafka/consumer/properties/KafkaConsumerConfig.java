package com.acroteq.ticketing.kafka.consumer.properties;

import jakarta.annotation.Nullable;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerConfig {

  @Nullable
  KafkaDeadLetterConfig deadLetter;
  @Nullable
  KafkaBackoffConfig backoff;
}
