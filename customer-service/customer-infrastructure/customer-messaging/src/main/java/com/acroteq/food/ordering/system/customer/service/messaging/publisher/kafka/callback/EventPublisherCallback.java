package com.acroteq.food.ordering.system.customer.service.messaging.publisher.kafka.callback;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Slf4j
@Component
public class EventPublisherCallback {

  public <T extends SpecificRecordBase> BiFunction<SendResult<String, T>, Throwable, SendResult<String, T>> getHandler(
      final String requestTopicName,
      final T message, final CustomerId customerId, final String messageType) {
    return (sendResult, exception) -> callback(requestTopicName,
                                               message,
                                               customerId,
                                               messageType,
                                               sendResult,
                                               exception);
  }

  private <T extends SpecificRecordBase> SendResult<String, T> callback(final String requestTopicName,
                                                                        final T message,
                                                                        final CustomerId customerId,
                                                                        final String messageType,
                                                                        final SendResult<String, T> sendResult,
                                                                        final Throwable exception) {
    if (exception == null) {
      final RecordMetadata metadata = sendResult.getRecordMetadata();
      log.info("Received response from Kafka for {} for customer Id {}" +
               " Topic {}, Partition {}, Offset {}, Timestamp {}",
               messageType,
               customerId,
               metadata.topic(),
               metadata.partition(),
               metadata.offset(),
               metadata.timestamp());
    } else {
      log.error("Error while sending {} message {} to topic {}", messageType, message, requestTopicName);
    }

    return sendResult;
  }
}
