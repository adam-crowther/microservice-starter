package com.acroteq.ticketing.airline.service.domain.config.config;

import com.acroteq.ticketing.airline.service.domain.properties.AirlineServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AirlineServiceConfig.class)
public class AirlineServiceConfiguration {}
