package com.acroteq.ticketing.order.service.config;

import com.acroteq.ticketing.order.service.client.api.OrdersApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Configuration
@Import({ MasterDataUploaderConfiguration.class,
          MasterDataGeneratorConfiguration.class,
          TestWebSecurityConfiguration.class })
public class IntegrationTestConfiguration {
  
  @Lazy
  @Bean
  OrdersApi ordersApi(@Value("${local.server.port}") final int serverPort) {
    final OrdersApi ordersApi = new OrdersApi();

    final String basePath = String.format("http://localhost:%d", serverPort);
    ordersApi.getApiClient()
             .setBasePath(basePath);

    return ordersApi;
  }
}
