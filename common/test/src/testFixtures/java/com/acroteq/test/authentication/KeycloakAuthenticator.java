package com.acroteq.test.authentication;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;

@RequiredArgsConstructor
public class KeycloakAuthenticator {

  private static final String CLIENT_ID = "ticketing";
  private final String keyCloakBaseUrl;
  private final String realm;

  @SneakyThrows
  public String authenticate(final String userName, final String password) {
    try (Keycloak keycloak = KeycloakBuilder.builder()
                                            .serverUrl(keyCloakBaseUrl)
                                            .realm(realm)
                                            .clientId(CLIENT_ID)
                                            .username(userName)
                                            .password(password)
                                            .grantType("password")
                                            .build()) {

      final TokenManager tokenManager = keycloak.tokenManager();

      final AccessTokenResponse accessToken = tokenManager.getAccessToken();
      return accessToken.getToken();
    }
  }
}
