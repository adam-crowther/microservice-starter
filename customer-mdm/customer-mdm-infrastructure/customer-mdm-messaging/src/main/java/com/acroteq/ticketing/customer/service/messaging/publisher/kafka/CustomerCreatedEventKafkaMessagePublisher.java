package com.acroteq.ticketing.customer.service.messaging.publisher.kafka;

import com.acroteq.ticketing.customer.service.domain.config.CustomerServiceConfig;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerCreatedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.messaging.mapper.CustomerCreatedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerCreatedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerCreatedEventKafkaMessagePublisher implements CustomerCreatedEventMessagePublisher {

  private final CustomerCreatedEventMessageFactory messageFactory;
  private final KafkaProducer<CustomerId, CustomerCreatedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<CustomerCreatedEventMessage> callbackHandler;
  private final CustomerServiceConfig config;

  @Override
  public void publish(final CustomerCreatedEvent event) {
    final CustomerId customerId = event.getCustomerId();

    log.info("Received CustomerCreatedEvent for order id: {}", customerId);

    final CustomerCreatedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getCustomerEvent()
                               .getTopicName();
    kafkaProducer.send(topic, customerId, message, callbackHandler::callback);

    log.info("CustomerCreatedEventMessage sent to kafka for order id: {}", customerId);
  }
}
