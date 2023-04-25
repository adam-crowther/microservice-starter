package com.acroteq.ticketing.airline.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-service")
public class AirlineServiceConfigData {

  private String airlineEventTopicName;
}
