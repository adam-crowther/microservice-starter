package com.acroteq.ticketing.order.service.messaging.publisher.kafka.payment;

import com.acroteq.kafka.producer.service.KafkaProducer;
import com.acroteq.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelRequestMessage;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentCancelRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.properties.OrderServiceConfig;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentCancelRequestMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCancelRequestKafkaMessagePublisher implements PaymentCancelRequestMessagePublisher {

  private final PaymentCancelRequestMessageFactory messageFactory;
  private final KafkaProducer<PaymentCancelRequestMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentCancelRequestMessage> callbackHandler;
  private final OrderServiceConfig config;

  @Override
  public void publish(final OrderCancelledEvent event) {
    final Long orderId = event.getOrder()
                              .getId()
                              .getValue();
    log.info("Publishing OrderCancelledEvent for order id {}", orderId);

    final PaymentCancelRequestMessage message = messageFactory.convert(event);
    final String topic = config.getPayment()
                               .getRequestTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentRequestMessage sent to Kafka for order id {}", orderId);
  }
}
