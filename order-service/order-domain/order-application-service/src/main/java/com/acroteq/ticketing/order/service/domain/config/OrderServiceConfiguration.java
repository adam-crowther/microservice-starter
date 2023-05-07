package com.acroteq.ticketing.order.service.domain.config;

import com.acroteq.ticketing.order.service.domain.properties.OrderServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OrderServiceConfig.class)
public class OrderServiceConfiguration {}
