package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class OrderServiceContainer extends AbstractTicketingContainer<OrderServiceContainer> {

  private static final String ORDER_SERVICE_IMAGE_NAME = "adamcc/ticketing/order-container:0.1.0";

  private static final int EXPOSED_PORT = 8181;
  private static final String CONTAINER_NAME = "OrderService";

  public static OrderServiceContainer startOrderServiceContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                                                 final List<KafkaSslContainer> kafkaContainers,
                                                                 final SchemaRegistryContainer schemaRegistryContainer,
                                                                 final KeycloakContainer keycloakContainer) {
    final OrderServiceContainer orderServiceContainer = new OrderServiceContainer(postgreSqlContainer,
                                                                                  kafkaContainers,
                                                                                  schemaRegistryContainer,
                                                                                  keycloakContainer);
    orderServiceContainer.start();

    return orderServiceContainer;
  }

  @SuppressWarnings("resource")
  private OrderServiceContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                final List<KafkaSslContainer> kafkaContainers,
                                final SchemaRegistryContainer schemaRegistryContainer,
                                final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(ORDER_SERVICE_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainers,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
