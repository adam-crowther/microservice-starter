package com.acroteq.ticketing.order.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PaymentConfig {

  @NotNull String requestTopicName;
  @NotNull String responseTopicName;
}
