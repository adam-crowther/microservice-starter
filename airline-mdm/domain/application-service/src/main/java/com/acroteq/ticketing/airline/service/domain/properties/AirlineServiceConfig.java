package com.acroteq.ticketing.airline.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "airline-service")
public class AirlineServiceConfig {

  @NotNull Integer swaggerPort;
  @NotNull AirlineEventConfig airlineEvent;
}
