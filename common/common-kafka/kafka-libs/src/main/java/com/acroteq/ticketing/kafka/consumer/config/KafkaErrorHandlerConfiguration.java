package com.acroteq.ticketing.kafka.consumer.config;

import static org.springframework.kafka.KafkaException.Level.ERROR;

import com.acroteq.ticketing.kafka.consumer.exception.EventListenerMissingException;
import com.acroteq.ticketing.kafka.consumer.exception.MessageToDtoMapperMissingException;
import lombok.RequiredArgsConstructor;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.io.Serializable;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Configuration
public class KafkaErrorHandlerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaOperations<Object, Object> operations;
  private final KafkaConsumerConfig kafkaConsumerConfig;

  @ConditionalOnProperty(prefix = "kafka.consumer", name = "auto-offset-reset")
  @Bean
  public DefaultErrorHandler kafkaErrorHandler(final KafkaDeadLetterConfig deadLetterConfig) {
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
    return (consumerRecord, exception) -> getTopicPartitionSupplier(consumerRecord, deadLetterConfig);
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
