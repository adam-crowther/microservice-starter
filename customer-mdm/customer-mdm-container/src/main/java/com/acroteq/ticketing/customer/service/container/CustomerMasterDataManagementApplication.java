package com.acroteq.ticketing.customer.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class CustomerMasterDataManagementApplication {

  public static void main(final String[] args) {
    SpringApplication.run(CustomerMasterDataManagementApplication.class, args);
  }
}
