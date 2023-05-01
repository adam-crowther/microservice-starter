package com.acroteq.ticketing.kafka.producer.config;

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
@ConfigurationProperties(prefix = "kafka.producer.serialisation")
public class KafkaSerialisationConfig {

  @NotNull
  private final String keySerializerClass;
  @NotNull
  private final String valueSerializerClass;
  @NotNull
  private final String compressionType;
}