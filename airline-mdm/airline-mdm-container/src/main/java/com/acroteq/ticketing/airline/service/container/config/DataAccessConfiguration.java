package com.acroteq.ticketing.airline.service.container.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.acroteq.ticketing.airline.service.data.access" })
@EntityScan(basePackages = { "com.acroteq.ticketing.airline.service.data.access" })
@Configuration
public class DataAccessConfiguration {

}
