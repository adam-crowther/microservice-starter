package com.acroteq.ticketing.kafka.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {

  private final String keySerializerClass;
  private final String valueSerializerClass;
  private final String compressionType;
  private final String acks;
  private final Integer batchSize;
  private final Integer batchSizeBoostFactor;
  private final Integer lingerMs;
  private final Integer requestTimeoutMs;
  private final Integer retryCount;
}