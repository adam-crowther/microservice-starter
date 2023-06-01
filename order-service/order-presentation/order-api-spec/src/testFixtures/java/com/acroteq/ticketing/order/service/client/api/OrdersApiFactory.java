package com.acroteq.ticketing.order.service.client.api;

import com.acroteq.ticketing.order.service.client.invoker.ApiClient;
import org.springframework.web.reactive.function.client.WebClient;

public final class OrdersApiFactory {

  public static OrdersApi createOrdersApi(final int port, final String bearerToken) {
    final WebClient webClient = WebClient.builder()
                                         .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                                         .build();

    final String basePath = String.format("http://localhost:%d", port);
    final ApiClient apiClient = new ApiClient(webClient);
    apiClient.setBasePath(basePath);
    return new OrdersApi(apiClient);
  }

  private OrdersApiFactory() {
  }
}
