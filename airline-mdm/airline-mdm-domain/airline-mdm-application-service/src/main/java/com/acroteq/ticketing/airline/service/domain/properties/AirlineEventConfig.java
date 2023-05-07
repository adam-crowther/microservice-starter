package com.acroteq.ticketing.airline.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AirlineEventConfig {

  @NotNull String topicName;
}
