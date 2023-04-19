package com.acroteq.ticketing.kafka.producer.config;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.acroteq.ticketing.kafka.config.KafkaConfigData;
import com.acroteq.ticketing.kafka.config.KafkaProducerConfigData;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfigData kafkaConfigData;
  private final KafkaProducerConfigData kafkaProducerConfigData;

  @ConditionalOnProperty(prefix = "kafka-producer-config", name = "value-serializer-class")
  @Bean
  public KafkaTemplate<K, V> kafkaTemplate() {
    final Map<String, Object> kafkaProducerConfigMap = createKafkaProducerConfigMap();
    final ProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProducerConfigMap);
    return new KafkaTemplate<>(producerFactory);
  }

  private Map<String, Object> createKafkaProducerConfigMap() {
    return ImmutableMap.<String, Object>builder()
                       .put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers())
                       .put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl())
                       .put(KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getKeySerializerClass())
                       .put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getValueSerializerClass())
                       .put(BATCH_SIZE_CONFIG, kafkaProducerConfigData.getBatchSize())
                       .put(LINGER_MS_CONFIG, kafkaProducerConfigData.getLingerMs())
                       .put(COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.getCompressionType())
                       .put(ACKS_CONFIG, kafkaProducerConfigData.getAcks())
                       .put(REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigData.getRequestTimeoutMs())
                       .put(RETRIES_CONFIG, kafkaProducerConfigData.getRetryCount())
                       .build();
  }
}
