package com.acroteq.food.ordering.system.customer.service.config;

import com.acroteq.food.ordering.system.customer.service.domain.CustomerDomainService;
import com.acroteq.food.ordering.system.customer.service.domain.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@link CustomerDomainService} is implemented in the Domain Core, which should only contain business logic and no
 * platform or framework dependencies. It is therefore not annotated as a Spring Service or Component, because we don't
 * want a dependency from the domain core to Spring.  This Spring Configuration class configures the
 * {@link CustomerDomainServiceImpl} manually outside the domain core, and makes it available to the application as a
 * Spring Bean.
 */
@Configuration
public class BeanConfiguration {

  @Bean
  public CustomerDomainService customerDomainService() {
    return new CustomerDomainServiceImpl();
  }
}
