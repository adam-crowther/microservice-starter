package com.acroteq.ticketing.customer.service.domain.config;

import com.acroteq.ticketing.customer.service.domain.properties.CustomerServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomerServiceConfig.class)
public class CustomerServiceConfiguration {}
