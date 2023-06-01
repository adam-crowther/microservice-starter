package com.acroteq.ticketing.customer.service.client.api;

import com.acroteq.ticketing.customer.service.client.invoker.ApiClient;
import org.springframework.web.reactive.function.client.WebClient;

public final class CustomersApiFactory {

  public static CustomersApi createCustomersApi(final int port, final String bearerToken) {
    final WebClient webClient = WebClient.builder()
                                         .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                                         .build();

    final String basePath = String.format("http://localhost:%d", port);
    final ApiClient apiClient = new ApiClient(webClient);
    apiClient.setBasePath(basePath);
    return new CustomersApi(apiClient);
  }

  private CustomersApiFactory() {
  }
}
