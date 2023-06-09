package com.acroteq.ticketing.order.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "order-service")
public class OrderServiceConfig {

  @NotNull PaymentConfig payment;
  @NotNull AirlineApprovalConfig airlineApproval;
}
