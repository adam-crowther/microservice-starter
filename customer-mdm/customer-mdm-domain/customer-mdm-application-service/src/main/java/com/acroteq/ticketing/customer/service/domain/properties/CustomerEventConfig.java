package com.acroteq.ticketing.customer.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CustomerEventConfig {

  @NotNull String topicName;
}
