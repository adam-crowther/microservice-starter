package com.acroteq.ticketing.approval.service.container.config;

import com.acroteq.ticketing.approval.service.domain.AirlineDomainService;
import com.acroteq.ticketing.approval.service.domain.AirlineDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public AirlineDomainService airlineDomainService() {
    return new AirlineDomainServiceImpl();
  }

}
