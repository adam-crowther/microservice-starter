package com.acroteq.kafka.payment.avro.model;

import com.acroteq.test.kafka.TestKafkaProducer;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentPaidResponseMessageProducer {

  private final TestKafkaProducer<PaymentPaidResponseMessage> producer;

  @Value("${order-service.payment.response-topic-name}")
  private String topic;

  public void send(final PaymentPaidResponseMessage paymentPaidResponse) {
    producer.send(topic, paymentPaidResponse.getPaymentId(), paymentPaidResponse);
  }
}
