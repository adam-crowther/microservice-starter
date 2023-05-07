package com.acroteq.ticketing.approval.service.domain.config;

import com.acroteq.ticketing.approval.service.domain.properties.AirlineApprovalServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AirlineApprovalServiceConfig.class)
public class AirlineApprovalConfiguration {}
