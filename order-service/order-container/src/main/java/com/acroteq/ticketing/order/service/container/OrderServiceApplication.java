package com.acroteq.ticketing.order.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class OrderServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(OrderServiceApplication.class, args);
  }

}
