package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfigData;
import com.acroteq.ticketing.airline.service.domain.event.OrderRejectedEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineApprovalResponseMessageFactory;
import com.acroteq.ticketing.airline.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.order.avro.model.AirlineApprovalResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderRejectedKafkaMessagePublisher implements OrderRejectedMessagePublisher {

  private final AirlineApprovalResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, AirlineApprovalResponseMessage> kafkaProducer;
  private final AirlineServiceConfigData airlineServiceConfigData;
  private final EventPublisherCallback callback;

  @Override
  public void publish(final OrderRejectedEvent orderRejectedEvent) {
    final OrderId orderId = orderRejectedEvent.getOrderApproval()
                                              .getOrderId();
    log.info("Received OrderRejectedEvent for order id: {}", orderId);

    final AirlineApprovalResponseMessage message = messageFactory.createAirlineApprovalResponseMessage(
        orderRejectedEvent);

    final String topicName = airlineServiceConfigData.getAirlineApprovalResponseTopicName();
    kafkaProducer.send(topicName,
                       orderId,
                       message,
                       callback.getHandler(topicName, message, orderId, "AirlineApprovalResponseMessage"));

    log.info("AirlineApprovalResponseMessage sent to kafka at: {}", System.nanoTime());
  }
}
