package com.acroteq.ticketing.payment.service.messaging.publisher.kafka;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentFailedResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.payment.service.domain.config.PaymentServiceConfig;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;
import com.acroteq.ticketing.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.acroteq.ticketing.payment.service.messaging.mapper.payment.PaymentFailedResponseMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentFailedKafkaMessagePublisher implements PaymentFailedMessagePublisher {

  private final PaymentFailedResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, PaymentFailedResponseMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<PaymentFailedResponseMessage> callbackHandler;
  private final PaymentServiceConfig config;

  @Override
  public void publish(final PaymentFailedEvent event) {
    final OrderId orderId = event.getPayment()
                                 .getOrderId();
    log.info("Received PaymentFailedEvent for order id: {}", orderId);

    final PaymentFailedResponseMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getPaymentResponse()
                               .getTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("PaymentResponseMessage sent to kafka for order id: {}", orderId);
  }
}
