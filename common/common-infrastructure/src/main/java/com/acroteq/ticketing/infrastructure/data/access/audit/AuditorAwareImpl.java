package com.acroteq.ticketing.infrastructure.data.access.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    final SecurityContext context = SecurityContextHolder.getContext();

    return Optional.of(context)
                   .map(SecurityContext::getAuthentication)
                   .map(Authentication::getName);
  }
}