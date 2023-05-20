package com.acroteq.ticketing.infrastructure.data.access.audit

import groovy.transform.CompileDynamic
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Shared
import spock.lang.Specification

import static org.mockito.Mockito.mockStatic
import static org.mockito.Mockito.when

/**
 * Here we use Mockito for mocking instead of Spock, because Spock can't mock static methods of Java classes.
 **/
@CompileDynamic
class AuditorAwareImplSpec extends Specification {

  static final String USERNAME = 'adamcc'

  @Mock
  @Shared
  SecurityContext securityContext

  @Mock
  @Shared
  Authentication authentication

  AuditorAware auditorAware = new AuditorAwareImpl()

  def setupSpec() {
    MockitoAnnotations.openMocks(this)
    def mockedSecurityContextHolder = mockStatic(SecurityContextHolder)
    mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext)
  }

  def cleanup() {
    Mockito.reset(authentication, securityContext)
  }

  def 'getCurrentAuditor() should return the user from the Spring Security context'() {
    given:
    when(securityContext.authentication).thenReturn(authentication)
    when(authentication.name).thenReturn(USERNAME)

    when:
    def currentAuditor = auditorAware.currentAuditor

    then:
    currentAuditor.isPresent()
    currentAuditor.get() == USERNAME
  }

  def 'if there is no name on the authentication, then getCurrentAuditor() should an empty Optional'() {
    given:
    when(securityContext.authentication).thenReturn(authentication)

    when:
    def currentAuditor = auditorAware.currentAuditor

    then:
    currentAuditor.isEmpty()
  }

  def 'if there is no Authentication on the security context, then getCurrentAuditor() should an empty Optional'() {
    when:
    def currentAuditor = auditorAware.currentAuditor

    then:
    currentAuditor.isEmpty()
  }
}
