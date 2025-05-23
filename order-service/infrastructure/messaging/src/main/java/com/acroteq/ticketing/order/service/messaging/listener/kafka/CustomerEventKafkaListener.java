package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.kafka.consumer.service.KafkaEntityEventMessageHandler;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.customer.CustomerEventMapper;
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
public class CustomerEventKafkaListener {

  private final KafkaEntityEventMessageHandler messageHandler;

  public CustomerEventKafkaListener(
      final CustomerEventMapper customerEventMapper,
      final CustomerEventMessageListener listener) {
    messageHandler = new KafkaEntityEventMessageHandler(CustomerEventMessage.SCHEMA$.getName(),
                                                        customerEventMapper,
                                                        listener::customerCreatedOrUpdated,
                                                        listener::customerDeleted);
  }

  @KafkaListener(id = "${order-service.customer-event.consumer-group-id}",
                 topics = "${order-service.customer-event.topic-name}")
  public void receive(
      @Payload @Validated final List<? extends SpecificRecord> messages, @Header(RECEIVED_KEY) final List<String> keys,
      @Header(RECEIVED_PARTITION) final List<Integer> partitions, @Header(OFFSET) final List<Long> offsets) {
    log.info("{} customer events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
