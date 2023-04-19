package com.acroteq.ticketing.order.service.messaging.publisher.kafka.payment;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.order.service.domain.config.OrderServiceConfigData;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.messaging.mapper.PaymentRequestMessageFactory;
import com.acroteq.ticketing.order.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {

  private final PaymentRequestMessageFactory mapper;
  private final KafkaProducer<OrderId, PaymentRequestMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final OrderServiceConfigData configData;

  @Override
  public void publish(final OrderCreatedEvent event) {
    final OrderId orderId = event.getOrder()
                                 .getId();
    log.info("Publishing {} for order id {}",
             event.getClass()
                  .getSimpleName(),
             orderId);

    final String topic = configData.getAirlineApprovalRequestTopicName();
    final PaymentRequestMessage message = mapper.createPaymentRequestMessage(event);
    kafkaProducer.send(topic, orderId, message, callback.getHandler(topic, message, orderId, "PaymentRequestMessage"));

    log.info("PaymentRequestMessage sent to Kafka for order id {}", orderId);
  }
}
