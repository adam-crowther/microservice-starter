package com.acroteq.food.ordering.system.payment.service.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.acroteq.food.ordering.system.payment.service.data.access")
@EntityScan(basePackages = "com.acroteq.food.ordering.system.payment.service.data.access")
@Configuration
public class DataAccessConfiguration {

}
