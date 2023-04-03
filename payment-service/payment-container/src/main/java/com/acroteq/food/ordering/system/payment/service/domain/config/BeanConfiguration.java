package com.acroteq.food.ordering.system.payment.service.domain.config;

import com.acroteq.food.ordering.system.payment.service.domain.PaymentDomainService;
import com.acroteq.food.ordering.system.payment.service.domain.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public PaymentDomainService paymentDomainService() {
    return new PaymentDomainServiceImpl();
  }
}
