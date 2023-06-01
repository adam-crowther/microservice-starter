package com.acroteq.ticketing.test.e2e.api;

import com.acroteq.ticketing.airline.service.client.api.AirlinesApi;
import com.acroteq.ticketing.airline.service.client.api.AirlinesApiFactory;
import com.acroteq.ticketing.customer.service.client.api.CustomersApi;
import com.acroteq.ticketing.customer.service.client.api.CustomersApiFactory;
import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import com.acroteq.ticketing.order.service.client.api.OrdersApiFactory;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;

public final class ClientApiFactory {

  public static CustomersApi createCustomersApi(final TestDockerContainers containers, final String bearerToken) {
    final Integer customerMdmPort = containers.getCustomerMdmPort();
    return CustomersApiFactory.createCustomersApi(customerMdmPort, bearerToken);
  }

  public static AirlinesApi createAirlinesApi(final TestDockerContainers containers, final String bearerToken) {
    final Integer airlinesMdmPort = containers.getAirlineMdmPort();
    return AirlinesApiFactory.createAirlinesApi(airlinesMdmPort, bearerToken);
  }

  public static OrdersApi createOrdersApi(final TestDockerContainers containers, final String bearerToken) {
    final Integer orderServicePort = containers.getOrderServicePort();
    return OrdersApiFactory.createOrdersApi(orderServicePort, bearerToken);
  }

  private ClientApiFactory() {
  }
}
