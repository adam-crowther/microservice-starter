package com.acroteq.ticketing.order.service.producer;

import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.test.kafka.TestKafkaProducer;
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
