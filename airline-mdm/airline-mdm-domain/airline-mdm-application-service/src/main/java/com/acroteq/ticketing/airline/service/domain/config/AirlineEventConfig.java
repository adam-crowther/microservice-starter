package com.acroteq.ticketing.airline.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-service.airline-event")
public class AirlineEventConfig {

  @NotNull
  private String topicName;
}
