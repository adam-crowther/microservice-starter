package com.acroteq.ticketing.order.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "order-service.payment-request")
public class PaymentConfig {

  @NotNull String requestTopicName;
  @NotNull String responseTopicName;
}
