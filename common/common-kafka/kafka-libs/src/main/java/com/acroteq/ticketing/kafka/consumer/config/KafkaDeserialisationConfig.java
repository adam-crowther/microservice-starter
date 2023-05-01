package com.acroteq.ticketing.kafka.consumer.config;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "kafka.consumer.deserialisation")
public class KafkaDeserialisationConfig {

  @NotNull
  private final String keyDeserializerClass;
  @NotNull
  private final String valueDeserializerClass;
  @NotNull
  private final String specificAvroReaderKey;
  @NotNull
  private final String specificAvroReader;

}
