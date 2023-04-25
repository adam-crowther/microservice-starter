package com.acroteq.ticketing.approval.service.messaging.listener.kafka;

import static com.acroteq.ticketing.kafka.airline.avro.model.EventType.CREATED;
import static com.acroteq.ticketing.kafka.airline.avro.model.EventType.DELETED;
import static com.acroteq.ticketing.kafka.airline.avro.model.EventType.UPDATED;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineDto;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.approval.service.messaging.mapper.AirlineEventMessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.EventType;
import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventKafkaListener implements KafkaConsumer<AirlineEventMessage> {

  private final Map<EventType, Consumer<AirlineEventMessage>> messageHandlers //
      = Map.of(CREATED,
               this::airlineCreatedMessageHandler,
               UPDATED,
               this::airlineUpdatedMessageHandler,
               DELETED,
               this::airlineDeletedMessageHandler);

  private final AirlineEventMessageListener messageListener;
  private final AirlineEventMessageToDtoMapper mapper;


  @Override
  @KafkaListener(id = "${kafka-consumer-config.airline-event-consumer-group-id}",
                 topics = "${airline-approval-service.airline-event-topic-name}")
  public void receive(@Payload final List<AirlineEventMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airline events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final AirlineEventMessage message) {
    final Consumer<AirlineEventMessage> messageHandler = Optional.of(message)
                                                                 .map(AirlineEventMessage::getEventType)
                                                                 .map(messageHandlers::get)
                                                                 .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final AirlineEventMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final AirlineEventMessage message) {
    final EventType eventType = message.getEventType();
    return new UnsupportedOperationException("Unsupported event type " + eventType);
  }

  private void airlineCreatedMessageHandler(final AirlineEventMessage message) {
    log.info("Airline created with id {}", message.getId());
    final AirlineDto airlineEvent = mapper.convertMessageToDto(message);
    messageListener.airlineCreated(airlineEvent);
  }

  private void airlineUpdatedMessageHandler(final AirlineEventMessage message) {
    log.info("Airline updated with id {}", message.getId());
    final AirlineDto airlineEvent = mapper.convertMessageToDto(message);
    messageListener.airlineUpdated(airlineEvent);
  }

  private void airlineDeletedMessageHandler(final AirlineEventMessage message) {
    final Long airlineId = message.getId();
    log.info("Airline deleted with id {}", airlineId);
    messageListener.airlineDeleted(airlineId);
  }
}
