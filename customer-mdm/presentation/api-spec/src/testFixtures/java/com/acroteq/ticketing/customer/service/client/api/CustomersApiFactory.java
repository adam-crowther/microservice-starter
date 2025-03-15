package com.acroteq.ticketing.customer.service.client.api;

import com.acroteq.ticketing.customer.service.client.invoker.ApiClient;

public final class CustomersApiFactory {

  public static CustomersApi createCustomersApi(final int port, final String bearerToken) {
    final String basePath = String.format("http://localhost:%d", port);
    final ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(basePath);
    apiClient.setAccessToken(bearerToken);

    return new CustomersApi(apiClient);
  }

  private CustomersApiFactory() {
  }
}
