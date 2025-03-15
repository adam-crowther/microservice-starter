package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.test.container.KafkaSslContainer;
import com.acroteq.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class PaymentServiceContainer extends AbstractTicketingContainer<PaymentServiceContainer> {

  private static final String PAYMENT_SERVICE_IMAGE_NAME = "adamcc/ticketing/payment-container:0.1.0";

  private static final int EXPOSED_PORT = 8182;
  private static final String CONTAINER_NAME = "PaymentService";

  public static PaymentServiceContainer startPaymentServiceContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    final PaymentServiceContainer paymentServiceContainer = new PaymentServiceContainer(postgreSqlContainer,
                                                                                        kafkaContainers,
                                                                                        schemaRegistryContainer,
                                                                                        keycloakContainer);
    paymentServiceContainer.start();

    return paymentServiceContainer;
  }

  @SuppressWarnings("resource")
  private PaymentServiceContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(PAYMENT_SERVICE_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainers,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
