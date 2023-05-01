package com.acroteq.ticketing.order.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "order-service.payment-request")
public class PaymentConfig {

  @NotNull
  private String requestTopicName;

  @NotNull
  private String responseTopicName;
}
