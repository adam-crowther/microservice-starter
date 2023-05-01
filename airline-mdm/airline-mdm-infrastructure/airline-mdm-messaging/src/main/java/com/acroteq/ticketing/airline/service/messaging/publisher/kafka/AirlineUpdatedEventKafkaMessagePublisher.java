package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfig;
import com.acroteq.ticketing.airline.service.domain.event.AirlineUpdatedEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineUpdatedEventMessagePublisher;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineUpdatedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineUpdatedEventKafkaMessagePublisher implements AirlineUpdatedEventMessagePublisher {

  private final AirlineUpdatedEventMessageFactory messageFactory;
  private final KafkaProducer<AirlineId, AirlineUpdatedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineUpdatedEventMessage> callbackHandler;
  private final AirlineServiceConfig config;

  @Override
  public void publish(final AirlineUpdatedEvent event) {
    final AirlineId airlineId = event.getAirlineId();

    log.info("Received AirlineUpdatedEvent for order id: {}", airlineId);

    final AirlineUpdatedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getAirlineEvent()
                               .getTopicName();
    kafkaProducer.send(topic, airlineId, message, callbackHandler::callback);

    log.info("AirlineUpdatedEventMessage sent to kafka for order id: {}", airlineId);
  }
}
