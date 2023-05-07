package com.acroteq.ticketing.payment.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "payment-service.payment")
public class PaymentConfig {

  @NotNull String requestTopicName;
  @NotNull String responseTopicName;
}
