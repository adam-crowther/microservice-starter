package com.acroteq.ticketing.airline.service.domain.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "airline-service")
public class AirlineServiceConfig {

  @NotNull
  private Integer swaggerPort;
  @NotNull
  private AirlineEventConfig airlineEvent;
}
