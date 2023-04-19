package com.acroteq.ticketing.payment.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class PaymentServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PaymentServiceApplication.class, args);
  }
}
