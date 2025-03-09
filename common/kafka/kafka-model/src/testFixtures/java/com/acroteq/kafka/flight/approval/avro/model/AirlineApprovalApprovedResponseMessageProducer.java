package com.acroteq.ticketing.kafka.flight.approval.avro.model;

import com.acroteq.ticketing.test.kafka.TestKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirlineApprovalApprovedResponseMessageProducer {

  private final TestKafkaProducer<AirlineApprovalApprovedResponseMessage> producer;

  @Value("${order-service.airline-approval.response-topic-name}")
  private String topic;

  public void send(final AirlineApprovalApprovedResponseMessage airlineApprovalApprovedResponse) {
    producer.send(topic, airlineApprovalApprovedResponse.getOrderId(), airlineApprovalApprovedResponse);
  }
}
