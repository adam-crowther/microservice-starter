package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.kafka.consumer.service.KafkaEntityEventMessageHandler;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline.AirlineEventMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineEventMapper;
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

  public AirlineEventKafkaListener(
      final AirlineEventMapper airlineEventMapper,
      final AirlineEventMessageListener listener) {
    messageHandler = new KafkaEntityEventMessageHandler(AirlineEventMessage.SCHEMA$.getName(),
                                                        airlineEventMapper,
                                                        listener::airlineCreatedOrUpdated,
                                                        listener::airlineDeleted);
  }

  @KafkaListener(id = "${order-service.airline-event.consumer-group-id}",
                 topics = "${order-service.airline-event.topic-name}")
  public void receive(
      @Payload @Validated final List<? extends SpecificRecord> messages, @Header(RECEIVED_KEY) final List<String> keys,
      @Header(RECEIVED_PARTITION) final List<Integer> partitions, @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airline events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
