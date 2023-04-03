package com.acroteq.food.ordering.system.kafka.consumer.service.config;

import com.acroteq.food.ordering.system.kafka.config.data.KafkaConfigData;
import com.acroteq.food.ordering.system.kafka.config.data.KafkaConsumerConfigData;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.io.Serializable;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfigData kafkaConfigData;
  private final KafkaConsumerConfigData kafkaConsumerConfigData;

  @ConditionalOnProperty(prefix = "kafka-consumer-config", name = "value-deserializer-class")
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
    final Map<String, Object> kafkaConsumerConfigMap = createKafkaConsumerConfigMap();
    final ConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory<>(kafkaConsumerConfigMap);

    final ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setBatchListener(kafkaConsumerConfigData.getBatchListener());
    factory.setConcurrency(kafkaConsumerConfigData.getConcurrencyLevel());
    factory.setAutoStartup(kafkaConsumerConfigData.getAutoStartup());
    factory.getContainerProperties()
           .setPollTimeout(kafkaConsumerConfigData.getPollTimeoutMs());

    return factory;
  }

  private Map<String, Object> createKafkaConsumerConfigMap() {
    final int maxPartitionFetchBytes =
        kafkaConsumerConfigData.getMaxPartitionFetchBytesDefault() *
        kafkaConsumerConfigData.getMaxPartitionFetchBytesBoostFactor();
    return ImmutableMap.<String, Object>builder()
                       .put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers())
                       .put(KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getKeyDeserializerClass())
                       .put(VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getValueDeserializerClass())
                       .put(AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfigData.getAutoOffsetReset())
                       .put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl())
                       .put(kafkaConsumerConfigData.getSpecificAvroReaderKey(),
                            kafkaConsumerConfigData.getSpecificAvroReader())
                       .put(SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerConfigData.getSessionTimeoutMs())
                       .put(HEARTBEAT_INTERVAL_MS_CONFIG, kafkaConsumerConfigData.getHeartbeatIntervalMs())
                       .put(MAX_POLL_INTERVAL_MS_CONFIG, kafkaConsumerConfigData.getMaxPollIntervalMs())
                       .put(MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes)
                       .put(MAX_POLL_RECORDS_CONFIG, kafkaConsumerConfigData.getMaxPollRecords())
                       .build();
  }


}
