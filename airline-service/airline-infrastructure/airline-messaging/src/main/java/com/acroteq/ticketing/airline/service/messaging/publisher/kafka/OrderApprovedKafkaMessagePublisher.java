package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfigData;
import com.acroteq.ticketing.airline.service.domain.event.OrderApprovedEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
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
public class OrderApprovedKafkaMessagePublisher implements OrderApprovedMessagePublisher {

  private final AirlineApprovalResponseMessageFactory messageFactory;
  private final KafkaProducer<OrderId, AirlineApprovalResponseMessage> kafkaProducer;
  private final AirlineServiceConfigData configData;
  private final EventPublisherCallback callback;

  @Override
  public void publish(final OrderApprovedEvent orderApprovedEvent) {
    final OrderId orderId = orderApprovedEvent.getOrderApproval()
                                              .getOrderId();
    log.info("Received OrderApprovedEvent for order id: {}", orderId);

    final AirlineApprovalResponseMessage message = messageFactory.createAirlineApprovalResponseMessage(
        orderApprovedEvent);

    final String topicName = configData.getAirlineApprovalResponseTopicName();
    kafkaProducer.send(topicName,
                       orderId,
                       message,
                       callback.getHandler(topicName, message, orderId, "AirlineApprovalResponseMessage"));

    log.info("AirlineApprovalResponseMessage sent to kafka at: {}", System.nanoTime());
  }
}
