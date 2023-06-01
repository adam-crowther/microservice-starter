package com.acroteq.ticketing.test.authentication;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class KeycloakAuthenticator {

  private static final String FORM_DATA_CLIENT_ID = "client_id";
  private static final String FORM_DATA_GRANT_TYPE = "grant_type";
  private static final String FORM_DATA_RESPONSE_TYPE = "response_type";
  private static final String FORM_DATA_USERNAME = "username";
  private static final String FORM_DATA_PASSWORD = "password";

  private static final String CLIENT_ID = "ticketing";
  private static final String RESPONSE_TYPE = "access_token";
  private static final String GRANT_TYPE = "password";

  private final String keyCloakBaseUrl;
  private final String realm;

  @SneakyThrows
  public String authenticate(final String userName, final String password) {
    final WebClient webClient = WebClient.builder()
                                         .baseUrl(keyCloakBaseUrl)
                                         .defaultHeader(CONTENT_TYPE, APPLICATION_FORM_URLENCODED)
                                         .build();

    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add(FORM_DATA_CLIENT_ID, CLIENT_ID);
    formData.add(FORM_DATA_GRANT_TYPE, GRANT_TYPE);
    formData.add(FORM_DATA_RESPONSE_TYPE, RESPONSE_TYPE);
    formData.add(FORM_DATA_USERNAME, userName);
    formData.add(FORM_DATA_PASSWORD, password);
    final var body = fromFormData(formData);

    final String uri = String.format("realms/%s/protocol/openid-connect/token", realm);
    final AccessTokenResponse tokenResponse = webClient.post()
                                                       .uri(uri)
                                                       .body(body)
                                                       .retrieve()
                                                       .bodyToMono(AccessTokenResponse.class)
                                                       .blockOptional()
                                                       .orElseThrow();
    return tokenResponse.getToken();
  }
}
