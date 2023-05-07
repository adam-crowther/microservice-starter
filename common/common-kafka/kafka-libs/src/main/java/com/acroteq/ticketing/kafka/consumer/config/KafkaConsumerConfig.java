package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerConfig {

  @NotNull String autoOffsetReset;
  @NotNull Boolean batchListener;
  @NotNull Boolean autoStartup;
  @NotNull Integer concurrencyLevel;

  @NotNull KafkaPollingConfig polling;
  @NotNull KafkaGroupManagementConfig groupManagement;
  @NotNull KafkaDeserialisationConfig deserialisation;

  @Nullable
  KafkaDeadLetterConfig deadLetter;
  @Nullable
  KafkaBackoffConfig backoff;
}
