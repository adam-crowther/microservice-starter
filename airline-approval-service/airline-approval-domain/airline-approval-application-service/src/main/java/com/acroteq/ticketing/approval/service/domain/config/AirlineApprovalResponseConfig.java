package com.acroteq.ticketing.approval.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-approval-service.airline-approval-response")
public class AirlineApprovalResponseConfig {

  @NotNull
  private String topicName;
}
