package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfigData;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineEventMessagePublisher;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineEventMessageFactoryVisitor;
import com.acroteq.ticketing.airline.service.messaging.publisher.kafka.callback.AirlineEventPublisherCallback;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventKafkaMessagePublisher implements AirlineEventMessagePublisher {

  private final AirlineEventMessageFactoryVisitor airlineEventMessageFactory;
  private final KafkaProducer<AirlineId, AirlineEventMessage> kafkaProducer;
  private final AirlineEventPublisherCallback callback;
  private final AirlineServiceConfigData configData;

  @Override
  public void publish(final AirlineEvent event) {
    final AirlineId airlineId = event.getAirlineId();

    log.info("Received {} for order id: {}", event.getEventType(), airlineId);

    final AirlineEventMessage message = event.accept(airlineEventMessageFactory);

    final String topicName = configData.getAirlineEventTopicName();
    kafkaProducer.send(topicName,
                       airlineId,
                       message,
                       callback.getHandler(topicName, message, airlineId, "AirlineEventMessage"));

    log.info("AirlineEventMessage sent to kafka for order id: {}", airlineId);
  }
}
