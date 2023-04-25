package com.acroteq.ticketing.payment.service.container.config;

import com.acroteq.ticketing.payment.service.domain.CreditEntryDomainService;
import com.acroteq.ticketing.payment.service.domain.CreditEntryDomainServiceImpl;
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
  public CreditEntryDomainService creditEntryDomainService() {
    return new CreditEntryDomainServiceImpl();
  }
}
