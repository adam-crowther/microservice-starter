package com.acroteq.ticketing.test.extension;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

@Slf4j
public class KeycloakContainerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final KeycloakContainer keycloak = new KeycloakContainer();

  @Override
  public void beforeAll(final ExtensionContext context) {
    keycloak.start();
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    keycloak.stop();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return parameterContext.getParameter()
                           .getType()
                           .equals(KeycloakContainer.class);
  }

  @Override
  public KeycloakContainer resolveParameter(final ParameterContext parameterContext,
                                            final ExtensionContext extensionContext) {
    return keycloak;
  }
}