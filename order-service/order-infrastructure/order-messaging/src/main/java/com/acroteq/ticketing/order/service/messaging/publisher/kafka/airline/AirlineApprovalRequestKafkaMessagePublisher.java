package com.acroteq.ticketing.order.service.messaging.publisher.kafka.airline;

import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.AirlineApprovalRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.properties.OrderServiceConfig;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineApprovalRequestMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineApprovalRequestKafkaMessagePublisher implements AirlineApprovalRequestMessagePublisher {

  private final AirlineApprovalRequestMessageFactory messageFactory;
  private final KafkaProducer<AirlineApprovalRequestMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineApprovalRequestMessage> callbackHandler;
  private final OrderServiceConfig config;

  @Override
  public void publish(final OrderPaidEvent event) {
    final Long orderId = event.getOrder()
                              .getId()
                              .getValue();
    log.info("Publishing OrderPaidEvent for order id {}", orderId);

    final AirlineApprovalRequestMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getAirlineApproval()
                               .getRequestTopicName();
    kafkaProducer.send(topic, orderId, message, callbackHandler::callback);

    log.info("AirlineApprovalRequestMessage sent to Kafka for order id {}", orderId);
  }
}
