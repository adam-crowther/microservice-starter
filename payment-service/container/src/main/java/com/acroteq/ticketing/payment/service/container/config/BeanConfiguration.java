package com.acroteq.ticketing.payment.service.container.config;

import com.acroteq.ticketing.payment.service.domain.CreditBalanceDomainService;
import com.acroteq.ticketing.payment.service.domain.CreditBalanceDomainServiceImpl;
import com.acroteq.ticketing.payment.service.domain.PaymentDomainService;
import com.acroteq.ticketing.payment.service.domain.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public PaymentDomainService paymentDomainService() {
    return new PaymentDomainServiceImpl();
  }

  @Bean
  public CreditBalanceDomainService creditBalanceDomainService() {
    return new CreditBalanceDomainServiceImpl();
  }
}
