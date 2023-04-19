package com.acroteq.ticketing.order.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "order-service")
public class OrderServiceConfigData {

  private String paymentRequestTopicName;
  private String paymentResponseTopicName;
  private String airlineApprovalRequestTopicName;
  private String airlineApprovalResponseTopicName;
}
