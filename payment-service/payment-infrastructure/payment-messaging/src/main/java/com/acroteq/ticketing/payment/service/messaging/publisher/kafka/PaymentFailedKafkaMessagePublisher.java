package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.payment.service.domain.config.PaymentServiceConfigData;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.acroteq.ticketing.payment.service.messaging.mapper.PaymentResponseMessageFactory;
import com.acroteq.ticketing.payment.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentFailedKafkaMessagePublisher implements PaymentFailedMessagePublisher {

  private final PaymentResponseMessageFactory paymentResponseMessageFactory;
  private final KafkaProducer<OrderId, PaymentResponseMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final PaymentServiceConfigData configData;

  @Override
  public void publish(final PaymentFailedEvent domainEvent) {
    final OrderId orderId = domainEvent.getPayment()
                                       .getOrderId();

    log.info("Received PaymentFailedEvent for order id: {}", orderId);

    final PaymentResponseMessage paymentResponseMessage =
        paymentResponseMessageFactory.paymentFailedEventToPaymentResponseMessage(domainEvent);

    kafkaProducer.send(configData.getPaymentResponseTopicName(),
                       orderId,
                       paymentResponseMessage,
                       callback.getHandler(configData.getPaymentResponseTopicName(),
                                           paymentResponseMessage,
                                           orderId,
                                           "PaymentResponseMessage"));

    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
