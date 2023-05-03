package com.acroteq.ticketing.payment.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment-service.payment")
public class PaymentConfig {

  @NotNull
  private String requestTopicName;

  @NotNull
  private String responseTopicName;
}
