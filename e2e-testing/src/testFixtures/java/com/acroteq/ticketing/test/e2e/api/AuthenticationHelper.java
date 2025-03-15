package com.acroteq.ticketing.test.e2e.api;

import com.acroteq.test.authentication.KeycloakAuthenticator;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;

public final class AuthenticationHelper {

  private static final String USER_NAME = "adamcc";
  private static final String PASSWORD = "password";
  private static final String REALM = "acroteq";

  public static String authenticate(final TestDockerContainers containers) {
    final String authServerUrl = containers.getKeycloakAuthServerUrl();
    final KeycloakAuthenticator authenticator = new KeycloakAuthenticator(authServerUrl, REALM);
    return authenticator.authenticate(USER_NAME, PASSWORD);
  }

  private AuthenticationHelper() {
  }
}
