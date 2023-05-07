package com.acroteq.ticketing.kafka.consumer.config;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.BATCH;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.io.Serializable;
import java.util.Map;

@RequiredArgsConstructor
@ConfigurationPropertiesScan
@Configuration
public class KafkaConsumerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaConsumerConfig kafkaConsumerConfig;
  private final KafkaConsumerConfigFactory kafkaConsumerConfigFactory;
  private final DefaultErrorHandler kafkaErrorHandler;

  @ConditionalOnProperty(prefix = "kafka.consumer", name = "auto-offset-reset")
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
    final Map<String, Object> configMap = kafkaConsumerConfigFactory.createKafkaConsumerConfigMap();
    final ConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory<>(configMap);

    final ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setBatchListener(kafkaConsumerConfig.getBatchListener());
    factory.setConcurrency(kafkaConsumerConfig.getConcurrencyLevel());
    factory.setAutoStartup(kafkaConsumerConfig.getAutoStartup());

    final ContainerProperties containerProperties = factory.getContainerProperties();
    final KafkaPollingConfig pollingConfig = kafkaConsumerConfig.getPolling();
    containerProperties.setPollTimeout(pollingConfig.getPollTimeoutMs());
    containerProperties.setAckMode(BATCH);
    factory.setCommonErrorHandler(kafkaErrorHandler);

    return factory;
  }
}
