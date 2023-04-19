package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.payment.service.domain.config.PaymentServiceConfigData;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.acroteq.ticketing.payment.service.messaging.mapper.PaymentResponseMessageFactory;
import com.acroteq.ticketing.payment.service.messaging.publisher.kafka.callback.EventPublisherCallback;
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

    final PaymentResponseMessage message = messageFactory.createPaymentResponseMessage(event);
    final String topicName = configData.getPaymentResponseTopicName();
    kafkaProducer.send(topicName,
                       orderId,
                       message,
                       callback.getHandler(topicName, message, orderId, "PaymentResponseMessage"));
    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
