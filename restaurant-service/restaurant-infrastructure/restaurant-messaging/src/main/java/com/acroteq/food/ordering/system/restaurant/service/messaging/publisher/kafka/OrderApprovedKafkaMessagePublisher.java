package com.acroteq.food.ordering.system.restaurant.service.messaging.publisher.kafka;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseMessage;
import com.acroteq.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.acroteq.food.ordering.system.restaurant.service.domain.config.RestaurantServiceConfigData;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.acroteq.food.ordering.system.restaurant.service.messaging.mapper.RestaurantApprovalResponseMessageFactory;
import com.acroteq.food.ordering.system.restaurant.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderApprovedKafkaMessagePublisher implements OrderApprovedMessagePublisher {

  private final RestaurantApprovalResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, RestaurantApprovalResponseMessage> kafkaProducer;
  private final RestaurantServiceConfigData configData;
  private final EventPublisherCallback callback;

  @Override
  public void publish(final OrderApprovedEvent orderApprovedEvent) {
    final OrderId orderId = orderApprovedEvent.getOrderApproval()
                                              .getOrderId();
    log.info("Received OrderApprovedEvent for order id: {}", orderId);

    try {
      final RestaurantApprovalResponseMessage message =
          messageFactory.createRestaurantApprovalResponseMessage(orderApprovedEvent);

      final String topicName = configData.getRestaurantApprovalResponseTopicName();
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
