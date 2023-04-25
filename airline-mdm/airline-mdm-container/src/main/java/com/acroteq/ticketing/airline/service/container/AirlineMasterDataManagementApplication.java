package com.acroteq.ticketing.airline.service.container;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class AirlineMasterDataManagementApplication {

  public static void main(final String[] args) {
    SpringApplication.run(AirlineMasterDataManagementApplication.class, args);
  }
}
