package com.acroteq.food.ordering.system.order.service.messaging.publisher.kafka.payment;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.acroteq.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.messaging.mapper.PaymentRequestMessageFactory;
import com.acroteq.food.ordering.system.order.service.messaging.publisher.kafka.callback.EventPublisherCallback;
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

    try {
      final String topic = configData.getRestaurantApprovalRequestTopicName();
      final PaymentRequestMessage message = mapper.createPaymentRequestMessage(event);
      kafkaProducer.send(topic,
                         orderId,
                         message,
                         callback.getHandler(topic, message, orderId, "PaymentRequestMessage"));

      log.info("PaymentRequestMessage sent to Kafka for order id {}", orderId);
    } catch (final Exception e) {
      log.error("Error while sending PaymentRequestMessage to Kafka with order id {}", orderId, e);
    }
  }
}
