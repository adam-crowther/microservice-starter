package com.acroteq.ticketing.approval.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "airline-approval-service")
public class AirlineApprovalServiceConfig {

  @NotNull AirlineEventConfig airlineEvent;
  @NotNull AirlineApprovalConfig airlineApproval;
}
