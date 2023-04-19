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
@ConfigurationProperties(prefix = "kafka-consumer-config")
public class KafkaConsumerConfigData {

  private final String keyDeserializerClass;
  private final String valueDeserializerClass;
  private final String autoOffsetReset;
  private final String specificAvroReaderKey;
  private final String specificAvroReader;
  private final Boolean batchListener;
  private final Boolean autoStartup;
  private final Integer concurrencyLevel;
  private final Integer sessionTimeoutMs;
  private final Integer heartbeatIntervalMs;
  private final Integer maxPollIntervalMs;
  private final Long pollTimeoutMs;
  private final Integer maxPollRecords;
  private final Integer maxPartitionFetchBytesDefault;
  private final Integer maxPartitionFetchBytesBoostFactor;
}
