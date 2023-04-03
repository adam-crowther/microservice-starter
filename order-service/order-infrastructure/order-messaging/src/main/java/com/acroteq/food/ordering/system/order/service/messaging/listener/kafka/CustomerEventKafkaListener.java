package com.acroteq.food.ordering.system.order.service.messaging.listener.kafka;

import com.acroteq.food.ordering.system.kafka.consumer.service.KafkaConsumer;
import com.acroteq.food.ordering.system.kafka.order.avro.model.CustomerEventMessage;
import com.acroteq.food.ordering.system.kafka.order.avro.model.EventType;
import com.acroteq.food.ordering.system.order.service.domain.dto.customer.CustomerDto;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.food.ordering.system.order.service.messaging.mapper.CustomerEventMessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.acroteq.food.ordering.system.kafka.order.avro.model.EventType.CREATED;
import static com.acroteq.food.ordering.system.kafka.order.avro.model.EventType.UPDATED;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventKafkaListener implements KafkaConsumer<CustomerEventMessage> {

  private final Map<EventType, Consumer<CustomerEventMessage>> MESSAGE_HANDLERS = Map.of(CREATED,
                                                                                         this::customerCreatedMessageHandler,
                                                                                         UPDATED,
                                                                                         this::customerUpdatedMessageHandler,
                                                                                         EventType.DELETED,
                                                                                         this::customerDeletedMessageHandler);

  private final CustomerEventMessageListener messageListener;
  private final CustomerEventMessageToDtoMapper mapper;


  @Override
  @KafkaListener(id = "${kafka-consumer-config.customer-consumer-group-id}",
                 topics = "${order-service.customer-topic-name}")
  public void receive(@Payload final List<CustomerEventMessage> messages,
                      @Header(KafkaHeaders.RECEIVED_KEY) final List<String> keys,
                      @Header(KafkaHeaders.RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
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
                                                                    .map(MESSAGE_HANDLERS::get)
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
    final UUID customerId = UUID.fromString(message.getId());
    log.info("Customer deleted with id {}", customerId);
    messageListener.customerDeleted(customerId);
  }
}
