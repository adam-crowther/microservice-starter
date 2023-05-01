package com.acroteq.ticketing.kafka.consumer.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.KafkaException.Level.ERROR;
import static org.springframework.kafka.listener.ContainerProperties.AckMode.BATCH;

import com.acroteq.ticketing.kafka.config.KafkaConfig;
import com.acroteq.ticketing.kafka.consumer.exception.EventListenerMissingException;
import com.acroteq.ticketing.kafka.consumer.exception.MessageToDtoMapperMissingException;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@ConfigurationPropertiesScan
@Configuration
public class KafkaConsumerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaOperations<Object, Object> operations;
  private final KafkaConsumerConfig kafkaConsumerConfig;
  private final KafkaConfig kafkaConfig;

  @ConditionalOnProperty(prefix = "kafka.consumer", name = "auto-offset-reset")
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
    final Map<String, Object> configMap = createKafkaConsumerConfigMap();
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

    final KafkaDeadLetterConfig deadLetterConfig = kafkaConsumerConfig.getDeadLetter();
    if (deadLetterConfig != null) {
      final DefaultErrorHandler deadLetterTopicErrorHandler = createDeadLetterTopicErrorHandler(deadLetterConfig);
      factory.setCommonErrorHandler(deadLetterTopicErrorHandler);
    }

    return factory;
  }

  private Map<String, Object> createKafkaConsumerConfigMap() {
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
                       .put(kafkaConfig.getSchemaRegistryUrlKey(), kafkaConfig.getSchemaRegistryUrl())
                       .put(deserialisationConfig.getSpecificAvroReaderKey(),
                            deserialisationConfig.getSpecificAvroReader())
                       .put(SESSION_TIMEOUT_MS_CONFIG, groupManagementConfig.getSessionTimeoutMs())
                       .put(HEARTBEAT_INTERVAL_MS_CONFIG, groupManagementConfig.getHeartbeatIntervalMs())
                       .put(MAX_POLL_INTERVAL_MS_CONFIG, pollingConfig.getMaxPollIntervalMs())
                       .put(MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes)
                       .put(MAX_POLL_RECORDS_CONFIG, pollingConfig.getMaxPollRecords())
                       .build();
  }

  private DefaultErrorHandler createDeadLetterTopicErrorHandler(final KafkaDeadLetterConfig deadLetterConfig) {
    final var recoverer = new DeadLetterPublishingRecoverer(operations, getTopicPartitionSupplier(deadLetterConfig));

    final KafkaBackoffConfig backoffConfig = kafkaConsumerConfig.getBackoff();
    final BackOff backOff;
    if (backoffConfig != null) {
      backOff = createExponentialBackoff(backoffConfig);
    } else {
      backOff = new FixedBackOff(0, 0);
    }

    final DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

    // Do not try to recover from validation exceptions when validation failed
    errorHandler.addNotRetryableExceptions(SchemaValidationException.class);
    errorHandler.addNotRetryableExceptions(EventListenerMissingException.class);
    errorHandler.addNotRetryableExceptions(MessageToDtoMapperMissingException.class);

    errorHandler.setLogLevel(ERROR);

    return errorHandler;
  }

  private BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> getTopicPartitionSupplier(final KafkaDeadLetterConfig deadLetterConfig) {
    return ((consumerRecord, exception) -> getTopicPartitionSupplier(consumerRecord, deadLetterConfig));
  }

  private TopicPartition getTopicPartitionSupplier(final ConsumerRecord<?, ?> consumerRecord,
                                                   final KafkaDeadLetterConfig deadLetterConfig) {
    final String deadLetterTopicSuffix = deadLetterConfig.getSuffix();
    final String topicName = consumerRecord.topic();
    final String deadLetterTopicName = topicName + deadLetterTopicSuffix;
    return new TopicPartition(deadLetterTopicName, 0);
  }

  private ExponentialBackOffWithMaxRetries createExponentialBackoff(final KafkaBackoffConfig backoffConfig) {
    final ExponentialBackOffWithMaxRetries backOff =
        new ExponentialBackOffWithMaxRetries(backoffConfig.getMaxRetries());
    backOff.setInitialInterval(backoffConfig.getInitialIntervalInMilliseconds());
    backOff.setMultiplier(backoffConfig.getMultiplier());
    backOff.setMaxInterval(backoffConfig.getMaxIntervalInMilliSeconds());
    return backOff;
  }
}
