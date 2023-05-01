package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.ticketing.airline.service.domain.config.AirlineServiceConfig;
import com.acroteq.ticketing.airline.service.domain.event.AirlineDeletedEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineDeletedEventMessagePublisher;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineDeletedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineDeletedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineDeletedEventKafkaMessagePublisher implements AirlineDeletedEventMessagePublisher {

  private final AirlineDeletedEventMessageFactory messageFactory;
  private final KafkaProducer<AirlineId, AirlineDeletedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineDeletedEventMessage> callbackHandler;
  private final AirlineServiceConfig config;

  @Override
  public void publish(final AirlineDeletedEvent event) {
    final AirlineId airlineId = event.getAirlineId();

    log.info("Received AirlineDeletedEvent for order id: {}", airlineId);

    final AirlineDeletedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getAirlineEvent()
                               .getTopicName();
    kafkaProducer.send(topic, airlineId, message, callbackHandler::callback);

    log.info("AirlineDeletedEventMessage sent to kafka for order id: {}", airlineId);
  }
}
