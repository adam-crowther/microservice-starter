package com.acroteq.ticketing.customer.service.messaging.publisher.kafka;

import com.acroteq.ticketing.customer.service.domain.config.CustomerServiceConfigData;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerEventMessagePublisher;
import com.acroteq.ticketing.customer.service.messaging.mapper.CustomerEventMessageFactoryVisitor;
import com.acroteq.ticketing.customer.service.messaging.publisher.kafka.callback.EventPublisherCallback;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.kafka.order.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventKafkaMessagePublisher implements CustomerEventMessagePublisher {

  private final CustomerEventMessageFactoryVisitor customerEventMessageFactory;
  private final KafkaProducer<CustomerId, CustomerEventMessage> kafkaProducer;
  private final EventPublisherCallback callback;
  private final CustomerServiceConfigData configData;

  @Override
  public void publish(final CustomerEvent event) {
    final CustomerId customerId = event.getCustomerId();

    log.info("Received {} for order id: {}", event.getEventType(), customerId);

    final CustomerEventMessage message = event.accept(customerEventMessageFactory);

    final String topicName = configData.getCustomerTopicName();
    kafkaProducer.send(topicName,
                       customerId,
                       message,
                       callback.getHandler(topicName, message, customerId, "CustomerEventMessage"));

    log.info("CustomerEventMessage sent to kafka for order id: {}", customerId);
  }
}
