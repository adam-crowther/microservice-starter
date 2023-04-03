package com.acroteq.food.ordering.system.restaurant.service.messaging.publisher.kafka;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseMessage;
import com.acroteq.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.acroteq.food.ordering.system.restaurant.service.domain.config.RestaurantServiceConfigData;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.acroteq.food.ordering.system.restaurant.service.messaging.mapper.RestaurantApprovalResponseMessageFactory;
import com.acroteq.food.ordering.system.restaurant.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderRejectedKafkaMessagePublisher implements OrderRejectedMessagePublisher {

  private final RestaurantApprovalResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, RestaurantApprovalResponseMessage> kafkaProducer;
  private final RestaurantServiceConfigData restaurantServiceConfigData;
  private final EventPublisherCallback callback;

  @Override
  public void publish(final OrderRejectedEvent orderRejectedEvent) {
    final OrderId orderId = orderRejectedEvent.getOrderApproval()
                                              .getOrderId();
    log.info("Received OrderRejectedEvent for order id: {}", orderId);

    try {
      final RestaurantApprovalResponseMessage message =
          messageFactory
              .createRestaurantApprovalResponseMessage(orderRejectedEvent);

      final String topicName = restaurantServiceConfigData.getRestaurantApprovalResponseTopicName();
      kafkaProducer.send(topicName,
                         orderId,
                         message,
                         callback.getHandler(topicName,
                                             message,
                                             orderId,
                                             "RestaurantApprovalResponseMessage"));

      log.info("RestaurantApprovalResponseMessage sent to kafka at: {}", System.nanoTime());
    } catch (final Exception e) {
      log.error("Error while sending RestaurantApprovalResponseMessage message" +
                " to kafka with order id: {}, error: {}", orderId, e.getMessage());
    }
  }
}
