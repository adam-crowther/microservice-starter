package com.acroteq.ticketing.approval.service.messaging.publisher.kafka;

import com.acroteq.ticketing.approval.service.domain.config.AirlineServiceConfig;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.approval.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import com.acroteq.ticketing.approval.service.messaging.mapper.approval.AirlineApprovalRejectedResponseMessageFactory;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRejectedResponseMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderRejectedKafkaMessagePublisher implements OrderRejectedMessagePublisher {

  private final AirlineApprovalRejectedResponseMessageFactory messageFactory;
  private final KafkaProducer<AirlineApprovalRejectedResponseMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineApprovalRejectedResponseMessage> callbackHandler;
  private final AirlineServiceConfig config;

  @Override
  public void publish(final OrderRejectedEvent event) {
    final Long orderId = event.getOrderApproval()
                              .getOrderId()
                              .getValue();
    log.info("Received OrderRejectedEvent for order id: {}", orderId);

    final AirlineApprovalRejectedResponseMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getAirlineApproval()
                               .getResponseTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("AirlineApprovalResponseMessage sent to kafka at: {}", System.nanoTime());
  }
}
