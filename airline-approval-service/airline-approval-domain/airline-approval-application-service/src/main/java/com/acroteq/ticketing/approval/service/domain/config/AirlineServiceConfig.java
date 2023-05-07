package com.acroteq.ticketing.approval.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Value
@ConfigurationProperties(prefix = "airline-approval-service")
public class AirlineServiceConfig {

  @NotNull AirlineEventConfig airlineEvent;
  @NotNull AirlineApprovalConfig airlineApproval;
}
