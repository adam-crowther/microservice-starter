package com.acroteq.ticketing.order.service.messaging.publisher.kafka.airline;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.kafka.order.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.order.service.domain.config.OrderServiceConfigData;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.OrderPaidAirlineRequestMessagePublisher;
import com.acroteq.ticketing.order.service.messaging.mapper.AirlineApprovalRequestMessageFactory;
import com.acroteq.ticketing.order.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidAirlineRequestMessagePublisher {

  private final AirlineApprovalRequestMessageFactory mapper;
  private final KafkaProducer<OrderId, AirlineApprovalRequestMessage> kafkaProducer;
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

    final String topic = configData.getAirlineApprovalRequestTopicName();
    final AirlineApprovalRequestMessage message = mapper.createAirlineApprovalRequestMessage(event);

    kafkaProducer.send(topic,
                       orderId,
                       message,
                       callback.getHandler(topic, message, orderId, "AirlineApprovalRequestMessage"));

    log.info("AirlineApprovalRequestMessage sent to Kafka for order id {}", orderId);
  }
}
