package com.acroteq.ticketing.payment.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "payment-service")
public class PaymentServiceConfig {

  @NotNull PaymentConfig payment;
}
