package com.acroteq.ticketing.customer.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "customer-service.customer-event")
public class CustomerEventConfig {

  @NotNull
  private String topicName;
}
