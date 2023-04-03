package com.acroteq.food.ordering.system.payment.service.messaging.publisher.kafka;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.acroteq.food.ordering.system.payment.service.domain.config.PaymentServiceConfigData;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.acroteq.food.ordering.system.payment.service.messaging.mapper.PaymentResponseMessageFactory;
import com.acroteq.food.ordering.system.payment.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCompletedKafkaMessagePublisher implements PaymentCompletedMessagePublisher {

  private final PaymentResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, PaymentResponseMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final PaymentServiceConfigData configData;

  @Override
  public void publish(final PaymentCompletedEvent event) {
    final OrderId orderId = event.getPayment()
                                 .getOrderId();
    log.info("Received PaymentCompletedEvent for order id: {}", orderId);

    try {
      final PaymentResponseMessage message = messageFactory.createPaymentResponseMessage(event);
      final String topicName = configData.getPaymentResponseTopicName();
      kafkaProducer.send(topicName,
                         orderId,
                         message,
                         callback.getHandler(topicName,
                                             message,
                                             orderId,
                                             "PaymentResponseMessage"));
      log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
    } catch (final Exception e) {
      log.error("Error while sending PaymentResponseMessage message" +
                " to kafka with order id: {}, error: {}", orderId, e.getMessage());
    }
  }
}
