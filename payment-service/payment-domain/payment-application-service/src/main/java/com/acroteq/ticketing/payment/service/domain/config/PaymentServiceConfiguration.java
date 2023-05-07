package com.acroteq.ticketing.payment.service.domain.config;

import com.acroteq.ticketing.payment.service.domain.properties.PaymentServiceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PaymentServiceConfig.class)
public class PaymentServiceConfiguration {}
