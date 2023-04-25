package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.payment.service.domain.config.PaymentServiceConfigData;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.acroteq.ticketing.payment.service.messaging.mapper.PaymentResponseMessageFactory;
import com.acroteq.ticketing.payment.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCancelledKafkaMessagePublisher implements PaymentCancelledMessagePublisher {

  private final PaymentResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, PaymentResponseMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final PaymentServiceConfigData configData;

  @Override
  public void publish(final PaymentCancelledEvent domainEvent) {
    final OrderId orderId = domainEvent.getPayment()
                                       .getOrderId();

    log.info("Received PaymentCancelledEvent for order id: {}", orderId);

    final PaymentResponseMessage responseMessage = messageFactory.createPaymentResponseMessage(domainEvent);

    kafkaProducer.send(configData.getPaymentResponseTopicName(),
                       orderId,
                       responseMessage,
                       callback.getHandler(configData.getPaymentResponseTopicName(),
                                           responseMessage,
                                           orderId,
                                           "PaymentResponseMessage"));

    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
