package com.acroteq.kafka.consumer.config;

import static org.springframework.kafka.KafkaException.Level.ERROR;

import com.acroteq.kafka.consumer.exception.EventListenerMissingException;
import com.acroteq.kafka.consumer.exception.UnsupportedMessageTypeException;
import com.acroteq.kafka.consumer.properties.KafkaBackoffConfig;
import com.acroteq.kafka.consumer.properties.KafkaConsumerConfig;
import com.acroteq.kafka.consumer.properties.KafkaDeadLetterConfig;
import lombok.RequiredArgsConstructor;
import org.apache.avro.SchemaValidationException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "kafka.consumer.dead-letter", name = "suffix")
@Configuration
public class KafkaErrorHandlerConfiguration {

  private final KafkaOperations<Object, Object> operations;
  private final KafkaConsumerConfig kafkaConsumerConfig;

  // TODO: Test that the error handler works as expected
  // @ConditionalOnProperty(prefix = "spring.kafka.consumer", name = "auto-offset-reset")
  // @Bean
  // public <KeyT, ValueT, ContainerT extends AbstractMessageListenerContainer<KeyT, ValueT>> ContainerCustomizer<KeyT,
  //     ValueT, ContainerT> containerCustomizer(
  //     final AbstractKafkaListenerContainerFactory<ContainerT, KeyT, ValueT> factory,
  //     final DefaultErrorHandler kafkaErrorHandler) {
  //   final ContainerCustomizer<KeyT, ValueT, ContainerT> customizer = createCustomizer(kafkaErrorHandler);
  //   factory.setContainerCustomizer(customizer);
  //   return customizer;
  // }
  //
  // private <KeyT, ValueT, ContainerT extends AbstractMessageListenerContainer<KeyT, ValueT>> ContainerCustomizer<KeyT,
  //     ValueT, ContainerT> createCustomizer(
  //     final DefaultErrorHandler kafkaErrorHandler) {
  //   return container -> container.setCommonErrorHandler(kafkaErrorHandler);
  // }

  @Bean
  public DefaultErrorHandler kafkaErrorHandler() {
    final KafkaDeadLetterConfig deadLetterConfig = kafkaConsumerConfig.getDeadLetter();
    final ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(operations, 
                                                                                getTopicPartitionSupplier(
                                                                                    deadLetterConfig));

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
    errorHandler.addNotRetryableExceptions(UnsupportedMessageTypeException.class);

    errorHandler.setLogLevel(ERROR);

    return errorHandler;
  }

  private BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> getTopicPartitionSupplier(
      final KafkaDeadLetterConfig deadLetterConfig) {
    return (consumerRecord, exception) -> getTopicPartitionSupplier(consumerRecord, deadLetterConfig);
  }

  private TopicPartition getTopicPartitionSupplier(
      final ConsumerRecord<?, ?> consumerRecord,
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
