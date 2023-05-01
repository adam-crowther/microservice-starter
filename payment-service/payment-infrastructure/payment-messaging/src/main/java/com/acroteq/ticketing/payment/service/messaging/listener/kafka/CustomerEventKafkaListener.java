package com.acroteq.ticketing.payment.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaMessageHandler;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerCreatedEventMessage;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerDeletedEventMessage;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerUpdatedEventMessage;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.CustomerEventMessageListener;
import com.acroteq.ticketing.payment.service.messaging.mapper.customer.CustomerCreatedEventMessageToDtoMapper;
import com.acroteq.ticketing.payment.service.messaging.mapper.customer.CustomerDeletedEventMessageToDtoMapper;
import com.acroteq.ticketing.payment.service.messaging.mapper.customer.CustomerUpdatedEventMessageToDtoMapper;
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
public class CustomerEventKafkaListener {

  private final KafkaMessageHandler kafkaMessageHandler;

  public CustomerEventKafkaListener(final CustomerCreatedEventMessageToDtoMapper createdEventMapper,
                                    final CustomerUpdatedEventMessageToDtoMapper updatedEventMapper,
                                    final CustomerDeletedEventMessageToDtoMapper deletedEventMapper,
                                    final CustomerEventMessageListener listener) {

    kafkaMessageHandler = KafkaMessageHandler.builder()
                                             .addMessageType(CustomerCreatedEventMessage.SCHEMA$.getName(),
                                                             createdEventMapper,
                                                             listener::customerCreated)
                                             .addMessageType(CustomerUpdatedEventMessage.SCHEMA$.getName(),
                                                             updatedEventMapper,
                                                             listener::customerUpdated)
                                             .addMessageType(CustomerDeletedEventMessage.SCHEMA$.getName(),
                                                             deletedEventMapper,
                                                             listener::customerDeleted)
                                             .build();
  }

  @KafkaListener(id = "${order-service.customer-event.consumer-group-id}",
                 topics = "${order-service.customer-event.topic-name}")
  public void receive(@Payload @Validated final List<? extends SpecificRecord> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} customer events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    kafkaMessageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
