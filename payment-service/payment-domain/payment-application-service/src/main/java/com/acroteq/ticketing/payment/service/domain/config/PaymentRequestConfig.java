package com.acroteq.ticketing.payment.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment-service.payment-request")
public class PaymentRequestConfig {

  @NotNull
  private String topicName;
}
