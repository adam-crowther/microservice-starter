package com.acroteq.ticketing.customer.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class CustomerServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
}
