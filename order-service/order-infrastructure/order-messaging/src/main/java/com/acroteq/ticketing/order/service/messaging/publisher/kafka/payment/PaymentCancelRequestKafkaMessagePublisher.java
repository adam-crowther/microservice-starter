package com.acroteq.ticketing.order.service.messaging.publisher.kafka.payment;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelRequestMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.order.service.domain.config.OrderServiceConfig;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentCancelRequestMessagePublisher;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentCancelRequestMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCancelRequestKafkaMessagePublisher implements PaymentCancelRequestMessagePublisher {

  private final PaymentCancelRequestMessageFactory messageFactory;
  private final KafkaProducer<OrderId, PaymentCancelRequestMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentCancelRequestMessage> callbackHandler;
  private final OrderServiceConfig config;

  @Override
  public void publish(final OrderCancelledEvent event) {
    final OrderId orderId = event.getOrder()
                                 .getId();
    log.info("Publishing OrderCancelledEvent for order id {}", orderId);

    final PaymentCancelRequestMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getPayment()
                               .getRequestTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentRequestMessage sent to Kafka for order id {}", orderId);
  }
}
