package com.acroteq.food.ordering.system.order.service.messaging.publisher.kafka.restaurant;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestMessage;
import com.acroteq.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.acroteq.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.messaging.mapper.RestaurantApprovalRequestMessageFactory;
import com.acroteq.food.ordering.system.order.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

  private final RestaurantApprovalRequestMessageFactory mapper;
  private final KafkaProducer<OrderId, RestaurantApprovalRequestMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final OrderServiceConfigData configData;

  @Override
  public void publish(final OrderPaidEvent event) {
    final OrderId orderId = event.getOrder()
                                 .getId();
    log.info("Publishing {} for order id {}",
             event.getClass()
                  .getSimpleName(),
             orderId);

    try {
      final String topic = configData.getRestaurantApprovalRequestTopicName();
      final RestaurantApprovalRequestMessage message = mapper.createRestaurantApprovalRequestMessage(event);

      kafkaProducer.send(topic,
                         orderId,
                         message,
                         callback.getHandler(topic, message, orderId, "RestaurantApprovalRequestMessage"));

      log.info("RestaurantApprovalRequestMessage sent to Kafka for order id {}", orderId);
    } catch (final Exception e) {
      log.error("Error while sending RestaurantApprovalRequestMessage to Kafka with order id {}", orderId, e);
    }
  }
}
