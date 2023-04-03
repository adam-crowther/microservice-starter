package com.acroteq.food.ordering.system.order.service.messaging.publisher.kafka.callback;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Slf4j
@Component
public class EventPublisherCallback {

  public <M extends SpecificRecordBase> BiFunction<SendResult<String, M>, Throwable, SendResult<String, M>> getHandler(
      final String requestTopicName,
      final M message, final OrderId orderId, final String messageType) {
    return (sendResult, exception) -> callback(requestTopicName,
                                               message,
                                               orderId,
                                               messageType,
                                               sendResult,
                                               exception);
  }

  private <M extends SpecificRecordBase>  SendResult<String, M> callback(final String requestTopicName,
                                              final M requestmessage,
                                              final OrderId orderId,
                                              final String messageType,
                                              final SendResult<String, M> sendResult,
                                              final Throwable exception) {
    if (exception == null) {
      final RecordMetadata metadata = sendResult.getRecordMetadata();
      log.info("Received response from Kafka for {} for order Id {}" +
               " Topic {}, Partition {}, Offset {}, Timestamp {}",
               messageType,
               orderId,
               metadata.topic(),
               metadata.partition(),
               metadata.offset(),
               metadata.timestamp());
    } else {
      log.error("Error while sending {} message {} to topic {}", messageType, requestmessage, requestTopicName);
    }

    return sendResult;
  }
}
