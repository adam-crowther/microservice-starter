package com.acroteq.ticketing.approval.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-approval-service")
public class AirlineServiceConfigData {

  private String airlineEventTopicName;
  private String airlineApprovalRequestTopicName;
  private String airlineApprovalResponseTopicName;
}
