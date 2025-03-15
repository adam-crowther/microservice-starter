package com.acroteq.ticketing.approval.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.kafka.consumer.service.KafkaEntityEventMessageHandler;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.approval.service.messaging.mapper.airline.AirlineEventMessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Component
public class AirlineEventKafkaListener {

  private final KafkaEntityEventMessageHandler messageHandler;

  public AirlineEventKafkaListener(final AirlineEventMessageToDtoMapper airlineEventMapper,
                                   final AirlineEventMessageListener listener) {
    messageHandler = new KafkaEntityEventMessageHandler(AirlineEventMessage.SCHEMA$.getName(),
                                                        airlineEventMapper,
                                                        listener::airlineCreatedOrUpdated,
                                                        listener::airlineDeleted);
  }

  @KafkaListener(id = "${airline-approval-service.airline-event.consumer-group-id}",
                 topics = "${airline-approval-service.airline-event.topic-name}")
  public void receive(@Payload @Validated final List<? extends SpecificRecord> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airline events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
