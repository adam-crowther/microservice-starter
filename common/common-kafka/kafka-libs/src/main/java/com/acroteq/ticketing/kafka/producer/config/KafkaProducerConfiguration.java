package com.acroteq.ticketing.kafka.producer.config;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.acroteq.ticketing.kafka.producer.properties.KafkaProducerConfig;
import com.acroteq.ticketing.kafka.producer.properties.KafkaSerialisationConfig;
import com.acroteq.ticketing.kafka.properties.KafkaConfig;
import com.google.common.collect.ImmutableMap;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
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

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConfig kafkaConfig;
  private final KafkaProducerConfig kafkaProducerConfig;

  @ConditionalOnProperty(prefix = "kafka.producer", name = "acks")
  @Bean
  public KafkaTemplate<K, V> kafkaTemplate() {
    final Map<String, Object> kafkaProducerConfigMap = createKafkaProducerConfigMap();
    final ProducerFactory<K, V> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProducerConfigMap);
    return new KafkaTemplate<>(producerFactory);
  }

  private Map<String, Object> createKafkaProducerConfigMap() {
    final KafkaSerialisationConfig serialisationConfig = kafkaProducerConfig.getSerialisation();
    return ImmutableMap.<String, Object>builder()
                       .put(BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers())
                       .put(SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getSchemaRegistryUrl())
                       .put(VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName())
                       .put(KEY_SERIALIZER_CLASS_CONFIG, serialisationConfig.getKeySerializerClass())
                       .put(VALUE_SERIALIZER_CLASS_CONFIG, serialisationConfig.getValueSerializerClass())
                       .put(BATCH_SIZE_CONFIG, kafkaProducerConfig.getBatchSize())
                       .put(LINGER_MS_CONFIG, kafkaProducerConfig.getLingerMs())
                       .put(COMPRESSION_TYPE_CONFIG, serialisationConfig.getCompressionType())
                       .put(ACKS_CONFIG, kafkaProducerConfig.getAcks())
                       .put(REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfig.getRequestTimeoutMs())
                       .put(RETRIES_CONFIG, kafkaProducerConfig.getRetryCount())
                       .build();
  }
}
