package com.acroteq.ticketing.customer.service.messaging.publisher.kafka;

import com.acroteq.ticketing.customer.service.domain.config.CustomerServiceConfig;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerEventMessagePublisher;
import com.acroteq.ticketing.customer.service.messaging.mapper.CustomerEventMessageFactory;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import com.acroteq.ticketing.kafka.producer.service.callback.KafkaPublisherCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventKafkaMessagePublisher implements CustomerEventMessagePublisher {

  private final CustomerEventMessageFactory messageFactory;
  private final KafkaProducer<CustomerEventMessage> kafkaProducer;
  private final KafkaPublisherCallbackHandler<CustomerEventMessage> callbackHandler;
  private final CustomerServiceConfig config;

  @Override
  public void publish(final CustomerEvent event) {
    final Long customerId = event.getCustomer()
                                 .getId()
                                 .getValue();

    log.info("Publishing CustomerEvent for order id: {}", customerId);

    final CustomerEventMessage message = messageFactory.convertEventToMessage(event);
    final String topic = config.getCustomerEvent()
                               .getTopicName();
    kafkaProducer.send(topic, customerId, message, callbackHandler::callback);

    log.info("CustomerEventMessage sent to kafka for order id: {}", customerId);
  }

  @Override
  public void publishDelete(final Long customerId) {
    log.info("Publishing CustomerEvent (delete) for order id: {}", customerId);

    final String topic = config.getCustomerEvent()
                               .getTopicName();
    // By convention, sending a null value constitutes a 'terminal' event, which deletes the entity.
    kafkaProducer.send(topic, customerId, null, callbackHandler::callback);

    log.info("CustomerEventMessage (delete) sent to kafka for order id: {}", customerId);
  }
}
