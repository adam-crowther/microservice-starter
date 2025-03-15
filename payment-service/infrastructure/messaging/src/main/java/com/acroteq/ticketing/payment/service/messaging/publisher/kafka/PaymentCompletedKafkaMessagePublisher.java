package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.kafka.producer.service.KafkaProducer;
import com.acroteq.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.acroteq.ticketing.payment.service.domain.properties.PaymentServiceConfig;
import com.acroteq.ticketing.payment.service.messaging.mapper.payment.PaymentPaidResponseMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentCompletedKafkaMessagePublisher implements PaymentCompletedMessagePublisher {

  private final PaymentPaidResponseMessageFactory messageFactory;
  private final KafkaProducer<PaymentPaidResponseMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentPaidResponseMessage> callbackHandler;
  private final PaymentServiceConfig config;

  @Override
  public void publish(final PaymentCompletedEvent event) {
    final Long orderId = event.getPayment()
                              .getOrderId()
                              .getValue();
    log.info("Received PaymentCompletedEvent for order id: {}", orderId);

    final PaymentPaidResponseMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getPayment()
                               .getResponseTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
