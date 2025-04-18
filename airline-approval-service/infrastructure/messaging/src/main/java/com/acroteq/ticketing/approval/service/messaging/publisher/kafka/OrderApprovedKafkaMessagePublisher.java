package com.acroteq.ticketing.approval.service.messaging.publisher.kafka;

import com.acroteq.kafka.producer.service.KafkaProducer;
import com.acroteq.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;
import com.acroteq.ticketing.approval.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.acroteq.ticketing.approval.service.domain.properties.AirlineApprovalServiceConfig;
import com.acroteq.ticketing.approval.service.messaging.mapper.approval.AirlineApprovalApprovedResponseMessageFactory;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderApprovedKafkaMessagePublisher implements OrderApprovedMessagePublisher {

  private final AirlineApprovalApprovedResponseMessageFactory messageFactory;
  private final KafkaProducer<AirlineApprovalApprovedResponseMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineApprovalApprovedResponseMessage> callbackHandler;
  private final AirlineApprovalServiceConfig config;

  @Override
  public void publish(final OrderApprovedEvent event) {
    final Long orderId = event.getOrderApproval()
                              .getOrderId()
                              .getValue();
    log.info("Received OrderApprovedEvent for order id: {}", orderId);

    final AirlineApprovalApprovedResponseMessage message = messageFactory.convert(event);
    final String topic = config.getAirlineApproval()
                               .getResponseTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("AirlineApprovalResponseMessage sent to kafka at: {}", System.nanoTime());
  }
}
