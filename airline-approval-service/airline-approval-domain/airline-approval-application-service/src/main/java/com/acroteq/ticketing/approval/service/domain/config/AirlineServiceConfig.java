package com.acroteq.ticketing.approval.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-approval-service")
public class AirlineServiceConfig {


  @NotNull
  private AirlineEventConfig airlineEvent;

  @NotNull
  private AirlineApprovalConfig airlineApproval;
}
