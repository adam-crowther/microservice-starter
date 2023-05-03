package com.acroteq.ticketing.infrastructure.data.access.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // TODO: Use Spring Security to return currently logged-in user
    // return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
    return Optional.of("adamcc");
  }
}