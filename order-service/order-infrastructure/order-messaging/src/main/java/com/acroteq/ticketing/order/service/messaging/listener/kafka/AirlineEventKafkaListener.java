package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineCreatedEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineDeletedEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineUpdatedEventMessage;
import com.acroteq.ticketing.kafka.consumer.KafkaMessageHandler;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineCreatedEventMessageToDtoMapper;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineDeletedEventMessageToDtoMapper;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineUpdatedEventMessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineEventKafkaListener {

  private final KafkaMessageHandler kafkaMessageHandler;

  public AirlineEventKafkaListener(final AirlineCreatedEventMessageToDtoMapper createdEventMapper,
                                   final AirlineUpdatedEventMessageToDtoMapper updatedEventMapper,
                                   final AirlineDeletedEventMessageToDtoMapper deletedEventMapper,
                                   final AirlineEventMessageListener listener) {

    kafkaMessageHandler = KafkaMessageHandler.builder()
                                             .addMessageType(AirlineCreatedEventMessage.SCHEMA$.getName(),
                                                             createdEventMapper,
                                                             listener::airlineCreated)
                                             .addMessageType(AirlineUpdatedEventMessage.SCHEMA$.getName(),
                                                             updatedEventMapper,
                                                             listener::airlineUpdated)
                                             .addMessageType(AirlineDeletedEventMessage.SCHEMA$.getName(),
                                                             deletedEventMapper,
                                                             listener::airlineDeleted)
                                             .build();
  }

  @KafkaListener(id = "${order-service.airline-event.consumer-group-id}",
                 topics = "${order-service.airline-event.topic-name}")
  public void receive(@Payload @Validated final List<? extends SpecificRecord> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airline events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    kafkaMessageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
