package com.acroteq.ticketing.customer.service.messaging.publisher.kafka;

import com.acroteq.ticketing.customer.service.domain.config.CustomerServiceConfig;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerDeletedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.messaging.mapper.CustomerDeletedEventMessageFactory;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerDeletedEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerDeletedEventKafkaMessagePublisher implements CustomerDeletedEventMessagePublisher {

  private final CustomerDeletedEventMessageFactory messageFactory;
  private final KafkaProducer<CustomerId, CustomerDeletedEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<CustomerDeletedEventMessage> callbackHandler;
  private final CustomerServiceConfig config;

  @Override
  public void publish(final CustomerDeletedEvent event) {
    final CustomerId customerId = event.getCustomerId();

    log.info("Received CustomerDeletedEvent for order id: {}", customerId);

    final CustomerDeletedEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getCustomerEvent()
                               .getTopicName();
    kafkaProducer.send(topic, customerId, message, callbackHandler::callback);

    log.info("CustomerDeletedEventMessage sent to kafka for order id: {}", customerId);
  }
}
