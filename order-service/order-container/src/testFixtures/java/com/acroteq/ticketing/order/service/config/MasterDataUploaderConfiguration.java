package com.acroteq.ticketing.order.service.config;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.order.service.data.MasterDataUploader;
import com.acroteq.ticketing.order.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.test.kafka.TestKafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MasterDataUploaderConfiguration {

  @Value("${order-service.airline-event.topic-name}")
  private String airlineEventTopic;
  @Value("${order-service.customer-event.topic-name}")
  private String customerEventTopic;

  @Bean
  MasterDataUploader<AirlineEventMessage> airlineMasterDataUploader(final TestKafkaProducer<AirlineEventMessage> kafkaProducer,
                                                                    final AirlineJpaRepository jpaRepository) {
    return new MasterDataUploader<>(airlineEventTopic, kafkaProducer, jpaRepository);
  }

  @Bean
  MasterDataUploader<CustomerEventMessage> customerMasterDataUploader(final TestKafkaProducer<CustomerEventMessage> kafkaProducer,
                                                                      final CustomerJpaRepository jpaRepository) {
    return new MasterDataUploader<>(customerEventTopic, kafkaProducer, jpaRepository);
  }
}
