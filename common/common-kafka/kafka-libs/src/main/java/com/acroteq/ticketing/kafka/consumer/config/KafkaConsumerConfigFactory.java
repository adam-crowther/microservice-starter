package com.acroteq.ticketing.kafka.consumer.config;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY;
import static io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import com.acroteq.ticketing.kafka.consumer.properties.KafkaConsumerConfig;
import com.acroteq.ticketing.kafka.consumer.properties.KafkaDeserialisationConfig;
import com.acroteq.ticketing.kafka.consumer.properties.KafkaGroupManagementConfig;
import com.acroteq.ticketing.kafka.consumer.properties.KafkaPollingConfig;
import com.acroteq.ticketing.kafka.properties.KafkaConfig;
import com.google.common.collect.ImmutableMap;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
class KafkaConsumerConfigFactory {

  private final KafkaConsumerConfig kafkaConsumerConfig;
  private final KafkaConfig kafkaConfig;

  Map<String, Object> createKafkaConsumerConfigMap() {
    final KafkaPollingConfig pollingConfig = kafkaConsumerConfig.getPolling();
    final int maxPartitionFetchBytes =
        pollingConfig.getMaxPartitionFetchBytesDefault() * pollingConfig.getMaxPartitionFetchBytesBoostFactor();
    final KafkaDeserialisationConfig deserialisationConfig = kafkaConsumerConfig.getDeserialisation();
    final KafkaGroupManagementConfig groupManagementConfig = kafkaConsumerConfig.getGroupManagement();
    return ImmutableMap.<String, Object>builder()
                       .put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers())
                       .put(KEY_DESERIALIZER_CLASS_CONFIG, deserialisationConfig.getKeyDeserializerClass())
                       .put(VALUE_DESERIALIZER_CLASS_CONFIG, deserialisationConfig.getValueDeserializerClass())
                       .put(AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfig.getAutoOffsetReset())
                       .put(VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName())
                       .put(SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getSchemaRegistryUrl())
                       .put(SPECIFIC_AVRO_READER_CONFIG, deserialisationConfig.getSpecificAvroReader())
                       .put(SESSION_TIMEOUT_MS_CONFIG, groupManagementConfig.getSessionTimeoutMs())
                       .put(HEARTBEAT_INTERVAL_MS_CONFIG, groupManagementConfig.getHeartbeatIntervalMs())
                       .put(MAX_POLL_INTERVAL_MS_CONFIG, pollingConfig.getMaxPollIntervalMs())
                       .put(MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes)
                       .put(MAX_POLL_RECORDS_CONFIG, pollingConfig.getMaxPollRecords())
                       .build();
  }
}
