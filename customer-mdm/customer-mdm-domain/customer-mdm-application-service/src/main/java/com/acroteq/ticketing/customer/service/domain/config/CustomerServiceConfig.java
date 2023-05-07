package com.acroteq.ticketing.customer.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "customer-service")
public class CustomerServiceConfig {

  @NotNull Integer swaggerPort;
  @NotNull CustomerEventConfig customerEvent;
}
