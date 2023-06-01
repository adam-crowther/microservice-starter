package com.acroteq.ticketing.order.service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestWebSecurityConfiguration {

  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  @Primary
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http.csrf(CsrfConfigurer::disable)
               .authorizeHttpRequests(requests -> requests.anyRequest()
                                                          .permitAll())
               .build();
  }
}