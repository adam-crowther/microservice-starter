package com.acroteq.ticketing.airline.service.container.config;

import com.acroteq.ticketing.airline.service.domain.AirlineDomainService;
import com.acroteq.ticketing.airline.service.domain.AirlineDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public AirlineDomainService airlineDomainService() {
    return new AirlineDomainServiceImpl();
  }

}
