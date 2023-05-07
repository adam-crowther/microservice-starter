package com.acroteq.ticketing.airline.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "airline-service.airline-event")
public class AirlineEventConfig {

  @NotNull String topicName;
}
