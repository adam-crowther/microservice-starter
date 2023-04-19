package com.acroteq.ticketing.kafka.producer.service.impl;

import static java.lang.System.lineSeparator;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import com.acroteq.ticketing.kafka.producer.exception.KafkaProducerException;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.PreDestroy;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerImpl<I extends BaseId, V extends SpecificRecordBase> implements KafkaProducer<I, V> {

  private static final String EOL = lineSeparator();

  private final KafkaTemplate<String, V> kafkaTemplate;

  @Override
  public void send(final String topicName,
                   final I id,
                   final V message,
                   final BiFunction<SendResult<String, V>, Throwable, SendResult<String, V>> callback) {
    try {
      log.info("Sending key {}, message {} to topic {}", id, message, topicName);
      final CompletableFuture<SendResult<String, V>> future = kafkaTemplate.send(topicName, id.toString(), message);
      future.handle(callback)
            .whenComplete(this::handleSendResult);
    } catch (final KafkaException exception) {
      // Error while producing
      throw new KafkaProducerException("Synchronous exception while sending a message to Kafka", exception);
    }
  }

  private void handleSendResult(final SendResult<String, V> sendResult, final Throwable exception) {
    if (exception == null) {
      // the record was successfully sent
      final RecordMetadata recordMetadata = sendResult.getRecordMetadata();
      log.info("Received new metadata. " + EOL + "Topic:" + recordMetadata.topic() + EOL + "Partition: "
               + recordMetadata.partition() + EOL + "Offset: " + recordMetadata.offset() + EOL + "Timestamp: "
               + recordMetadata.timestamp());
    } else {
      // Error while producing
      throw new KafkaProducerException("Asynchronous exception while sending a message to Kafka", exception);
    }
  }

  @PreDestroy
  public void close() {
    if (kafkaTemplate != null) {
      log.info("Closing kafka producer");
      kafkaTemplate.destroy();
    }
  }
}
