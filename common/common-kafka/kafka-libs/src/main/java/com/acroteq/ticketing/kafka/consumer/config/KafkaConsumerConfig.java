package com.acroteq.ticketing.kafka.consumer.config;


import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Nullable;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerConfig {

  @NotNull
  private final String autoOffsetReset;
  @NotNull
  private final Boolean batchListener;
  @NotNull
  private final Boolean autoStartup;
  @NotNull
  private final Integer concurrencyLevel;

  @NotNull
  private final KafkaPollingConfig polling;
  @NotNull
  final KafkaGroupManagementConfig groupManagement;
  @NotNull
  final KafkaDeserialisationConfig deserialisation;

  @Nullable
  private final KafkaDeadLetterConfig deadLetter;
  @Nullable
  private final KafkaBackoffConfig backoff;
}
