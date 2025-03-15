package com.acroteq.ticketing.order.service.config;

import com.acroteq.test.kafka.TestKafkaConsumer;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class KafkaConsumerConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value("${order-service.payment.request-topic-name}")
  private String paymentRequestTopic;
  @Value("${order-service.payment.consumer-group-id}")
  private String paymentGroup;
  @Value("${order-service.airline-approval.request-topic-name}")
  private String airlineApprovalRequestTopic;
  @Value("${order-service.airline-approval.consumer-group-id}")
  private String airlineApprovalGroup;

  @Bean
  TestKafkaConsumer<PaymentRequestMessage> paymentRequestMessageConsumer() {
    return new TestKafkaConsumer<>(bootstrapServers, paymentRequestTopic, paymentGroup);
  }

  @Bean
  TestKafkaConsumer<AirlineApprovalRequestMessage> airlineApprovalRequestMessageConsumer() {
    return new TestKafkaConsumer<>(bootstrapServers, airlineApprovalRequestTopic, airlineApprovalGroup);
  }
}
