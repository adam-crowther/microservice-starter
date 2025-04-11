package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.kafka.producer.service.KafkaProducer;
import com.acroteq.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelledResponseMessage;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.acroteq.ticketing.payment.service.domain.properties.PaymentServiceConfig;
import com.acroteq.ticketing.payment.service.messaging.mapper.payment.PaymentCancelledResponseMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCancelledKafkaMessagePublisher implements PaymentCancelledMessagePublisher {

  private final PaymentCancelledResponseMessageFactory messageFactory;
  private final KafkaProducer<PaymentCancelledResponseMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentCancelledResponseMessage> callbackHandler;
  private final PaymentServiceConfig config;

  @Override
  public void publish(final PaymentCancelledEvent event) {
    final Long orderId = event.getPayment()
                              .getOrderId()
                              .getValue();
    log.info("Received PaymentCancelledEvent for order id: {}", orderId);

    final PaymentCancelledResponseMessage message = messageFactory.convert(event);
    final String topic = config.getPayment()
                               .getResponseTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
