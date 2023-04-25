package com.acroteq.ticketing.payment.service.messaging.listener.kafka;

import static com.acroteq.ticketing.kafka.customer.avro.model.EventType.CREATED;
import static com.acroteq.ticketing.kafka.customer.avro.model.EventType.DELETED;
import static com.acroteq.ticketing.kafka.customer.avro.model.EventType.UPDATED;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.kafka.customer.avro.model.EventType;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDto;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.CustomerEventMessageListener;
import com.acroteq.ticketing.payment.service.messaging.mapper.CustomerEventMessageToDtoMapper;
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
public class CustomerEventKafkaListener implements KafkaConsumer<CustomerEventMessage> {

  private final Map<EventType, Consumer<CustomerEventMessage>> messageHandlers //
      = Map.of(CREATED,
               this::customerCreatedMessageHandler,
               UPDATED,
               this::customerUpdatedMessageHandler,
               DELETED,
               this::customerDeletedMessageHandler);

  private final CustomerEventMessageListener messageListener;
  private final CustomerEventMessageToDtoMapper mapper;


  @Override
  @KafkaListener(id = "${kafka-consumer-config.customer-event-consumer-group-id}",
                 topics = "${payment-service.customer-event-topic-name}")
  public void receive(@Payload final List<CustomerEventMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} customer events received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final CustomerEventMessage message) {
    final Consumer<CustomerEventMessage> messageHandler = Optional.of(message)
                                                                  .map(CustomerEventMessage::getEventType)
                                                                  .map(messageHandlers::get)
                                                                  .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final CustomerEventMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final CustomerEventMessage message) {
    final EventType eventType = message.getEventType();
    return new UnsupportedOperationException("Unsupported event type " + eventType);
  }

  private void customerCreatedMessageHandler(final CustomerEventMessage message) {
    log.info("Customer created with id {}", message.getId());
    final CustomerDto customerEvent = mapper.convertMessageToDto(message);
    messageListener.customerCreated(customerEvent);
  }

  private void customerUpdatedMessageHandler(final CustomerEventMessage message) {
    log.info("Customer updated with id {}", message.getId());
    final CustomerDto customerEvent = mapper.convertMessageToDto(message);
    messageListener.customerUpdated(customerEvent);
  }

  private void customerDeletedMessageHandler(final CustomerEventMessage message) {
    final Long customerId = message.getId();
    log.info("Customer deleted with id {}", customerId);
    messageListener.customerDeleted(customerId);
  }
}
