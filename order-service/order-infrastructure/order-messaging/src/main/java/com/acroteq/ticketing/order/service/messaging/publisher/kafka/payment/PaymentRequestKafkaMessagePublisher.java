package com.acroteq.ticketing.order.service.messaging.publisher.kafka.payment;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.order.service.domain.config.OrderServiceConfig;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentRequestMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentRequestKafkaMessagePublisher implements PaymentRequestMessagePublisher {

  private final PaymentRequestMessageFactory messageFactory;
  private final KafkaProducer<OrderId, PaymentRequestMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentRequestMessage> callbackHandler;
  private final OrderServiceConfig config;

  @Override
  public void publish(final OrderCreatedEvent event) {
    final OrderId orderId = event.getOrder()
                                 .getId();
    log.info("Publishing OrderCreatedEvent for order id {}", orderId);

    final PaymentRequestMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getPayment()
                               .getRequestTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentRequestMessage sent to Kafka for order id {}", orderId);
  }
}
