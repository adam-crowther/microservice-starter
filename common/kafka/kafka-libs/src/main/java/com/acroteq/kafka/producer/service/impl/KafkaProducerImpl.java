package com.acroteq.kafka.producer.service.impl;

import static java.lang.System.lineSeparator;

import com.acroteq.kafka.producer.exception.AsynchronousKafkaProducerException;
import com.acroteq.kafka.producer.exception.SynchronousKafkaProducerException;
import com.acroteq.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerImpl<MessageT extends SpecificRecord> implements KafkaProducer<MessageT> {

  private static final String EOL = lineSeparator();

  private final KafkaTemplate<String, MessageT> kafkaTemplate;

  @Override
  public void send(final String topicName,
                   final Long id,
                   final MessageT message,
                   final BiConsumer<SendResult<String, MessageT>, Throwable> callback) {
    try {
      log.info("Sending message key {} to topic {}", id, topicName);
      log.debug("Message {}", message);
      final CompletableFuture<SendResult<String, MessageT>> future = kafkaTemplate.send(topicName,
                                                                                        id.toString(),
                                                                                        message);
      future.handle(this::handleSendResult)
            .whenComplete(callback);
    } catch (final KafkaException exception) {
      // Error while producing
      throw new SynchronousKafkaProducerException(exception);
    }
  }

  private SendResult<String, MessageT> handleSendResult(final SendResult<String, MessageT> sendResult,
                                                        final Throwable exception) {
    if (exception == null) {
      // the record was successfully sent
      final RecordMetadata recordMetadata = sendResult.getRecordMetadata();
      log.info("Message send to kafka successfully. "
               + EOL
               + "Topic:"
               + recordMetadata.topic()
               + EOL
               + "Partition: "
               + recordMetadata.partition()
               + EOL
               + "Offset: "
               + recordMetadata.offset()
               + EOL
               + "Timestamp: "
               + recordMetadata.timestamp());
    } else {
      // Error while producing
      log.error("Error while sending message to Kafka.", exception);
      throw new AsynchronousKafkaProducerException(exception);
    }

    return sendResult;
  }

  @PreDestroy
  public void close() {
    log.info("Closing kafka producer");
    Optional.ofNullable(kafkaTemplate)
            .ifPresent(KafkaTemplate::destroy);
  }
}
