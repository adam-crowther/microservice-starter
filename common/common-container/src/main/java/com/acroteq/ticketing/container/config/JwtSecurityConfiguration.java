package com.acroteq.ticketing.container.config;

import com.acroteq.ticketing.container.properties.JwtMappingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(JwtMappingProperties.class)
public class JwtSecurityConfiguration {

  private final JwtMappingProperties jwtMappingProperties;

  /* The HttpSecurity methods all throw generic Exception. Nothing we can do. */
  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .and()
        .authorizeHttpRequests()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .and()
        .and()
        .oauth2Login();

    return http.build();
  }

  @Bean
  public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
    final JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
    final String authorityPrefix = jwtMappingProperties.getAuthorityPrefix()
                                                       .trim();

    converter.setAuthorityPrefix(authorityPrefix);
    return converter;
  }

  @Bean
  public JwtAuthenticationConverter customJwtAuthenticationConverter() {

    final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
    converter.setPrincipalClaimName(jwtMappingProperties.getPrincipalClaimName()
                                                        .trim());

    return converter;
  }
}
