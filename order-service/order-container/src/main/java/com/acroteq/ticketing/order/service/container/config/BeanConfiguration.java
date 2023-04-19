package com.acroteq.ticketing.order.service.container.config;

import com.acroteq.ticketing.order.service.domain.OrderDomainService;
import com.acroteq.ticketing.order.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@link OrderDomainService} is implemented in the Domain Core, which should only contain business logic and no
 * platform or framework dependencies. It is therefore not annotated as a Spring Service or Component, because we don't
 * want a dependency from the domain core to Spring.  This Spring Configuration class configures the
 * {@link OrderDomainServiceImpl} manually outside the domain core, and makes it available to the application as a
 * Spring Bean.
 */
@Configuration
public class BeanConfiguration {

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }
}
