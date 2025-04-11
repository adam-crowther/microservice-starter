package com.acroteq.ticketing.airline.service.messaging.publisher.kafka;

import com.acroteq.kafka.producer.service.KafkaProducer;
import com.acroteq.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import com.acroteq.ticketing.airline.service.domain.event.AirlineEvent;
import com.acroteq.ticketing.airline.service.domain.ports.output.message.publisher.AirlineEventMessagePublisher;
import com.acroteq.ticketing.airline.service.domain.properties.AirlineServiceConfig;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineEventMessageFactory;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventKafkaMessagePublisher implements AirlineEventMessagePublisher {

  private final AirlineEventMessageFactory messageFactory;
  private final KafkaProducer<AirlineEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<AirlineEventMessage> callbackHandler;
  private final AirlineServiceConfig config;

  @Override
  public void publish(final AirlineEvent event) {
    final Long airlineId = event.getAirline()
                                .getId()
                                .getValue();
    log.info("Publishing AirlineEvent for order id: {}", airlineId);

    final AirlineEventMessage message = messageFactory.convert(event);
    final String topic = config.getAirlineEvent()
                               .getTopicName();
    kafkaProducer.send(topic, airlineId, message, callbackHandler::callback);

    log.info("AirlineEventMessage sent to kafka for order id: {}", airlineId);
  }

  @Override
  public void publishDelete(final String code) {
    log.info("Publishing AirlineEvent (delete) for code: {}", code);

    final String topic = config.getAirlineEvent()
                               .getTopicName();
    // By convention, sending a null value constitutes a 'terminal' event, which deletes the entity.
    kafkaProducer.send(topic, code, null, callbackHandler::callback);

    log.info("AirlineEventMessage (delete) sent to kafka for code: {}", code);
  }
}
