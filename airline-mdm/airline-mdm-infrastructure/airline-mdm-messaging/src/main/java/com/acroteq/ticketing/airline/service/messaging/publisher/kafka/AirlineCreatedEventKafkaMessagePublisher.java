package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfig;
import com.acroteq.ticketing.airline.service.domain.event.AirlineCreatedEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineCreatedEventMessagePublisher;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineCreatedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineCreatedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineCreatedEventKafkaMessagePublisher implements AirlineCreatedEventMessagePublisher {

  private final AirlineCreatedEventMessageFactory messageFactory;
  private final KafkaProducer<AirlineId, AirlineCreatedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineCreatedEventMessage> callbackHandler;
  private final AirlineServiceConfig config;

  @Override
  public void publish(final AirlineCreatedEvent event) {
    final AirlineId airlineId = event.getAirlineId();

    log.info("Received AirlineCreatedEvent for order id: {}", airlineId);

    final AirlineCreatedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getAirlineEvent()
                               .getTopicName();
    kafkaProducer.send(topic, airlineId, message, callbackHandler::callback);

    log.info("AirlineCreatedEventMessage sent to kafka for order id: {}", airlineId);
  }
}
