package com.acroteq.ticketing.approval.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "airline-approval-service.airline-event")
public class AirlineEventConfig {

  @NotNull String topicName;
}
