package com.acroteq.food.ordering.system.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.acroteq.food.ordering.system")
public class OrderServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(OrderServiceApplication.class, args);
  }

}
