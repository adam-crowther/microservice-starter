package com.acroteq.food.ordering.system.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.acroteq.food.ordering.system")
public class PaymentServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PaymentServiceApplication.class, args);
  }
}
