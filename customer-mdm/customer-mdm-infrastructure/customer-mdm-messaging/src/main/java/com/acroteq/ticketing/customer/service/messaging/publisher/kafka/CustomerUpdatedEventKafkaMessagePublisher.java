package com.acroteq.ticketing.customer.service.messaging.publisher.kafka;

import com.acroteq.ticketing.customer.service.domain.config.CustomerServiceConfig;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerUpdatedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.messaging.mapper.CustomerUpdatedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerUpdatedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerUpdatedEventKafkaMessagePublisher implements CustomerUpdatedEventMessagePublisher {

  private final CustomerUpdatedEventMessageFactory messageFactory;
  private final KafkaProducer<CustomerId, CustomerUpdatedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<CustomerUpdatedEventMessage> callbackHandler;
  private final CustomerServiceConfig config;

  @Override
  public void publish(final CustomerUpdatedEvent event) {
    final CustomerId customerId = event.getCustomerId();

    log.info("Received CustomerUpdatedEvent for order id: {}", customerId);

    final CustomerUpdatedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getCustomerEvent()
                               .getTopicName();
    kafkaProducer.send(topic, customerId, message, callbackHandler::callback);

    log.info("CustomerUpdatedEventMessage sent to kafka for order id: {}", customerId);
  }
}
