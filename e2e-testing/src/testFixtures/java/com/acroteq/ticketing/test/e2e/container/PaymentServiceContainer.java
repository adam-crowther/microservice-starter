package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public final class PaymentServiceContainer extends AbstractTicketingContainer<PaymentServiceContainer> {

  private static final String PAYMENT_SERVICE_IMAGE_NAME = "adamcc/ticketing/payment-container:0.1.0";

  private static final int EXPOSED_PORT = 8182;
  private static final String CONTAINER_NAME = "PaymentService";

  public static PaymentServiceContainer startPaymentServiceContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                                                     final KafkaSslContainer kafkaContainer,
                                                                     final SchemaRegistryContainer schemaRegistryContainer,
                                                                     final KeycloakContainer keycloakContainer) {
    final PaymentServiceContainer paymentServiceContainer = new PaymentServiceContainer(postgreSqlContainer,
                                                                                        kafkaContainer,
                                                                                        schemaRegistryContainer,
                                                                                        keycloakContainer);
    paymentServiceContainer.start();

    return paymentServiceContainer;
  }

  @SuppressWarnings("resource")
  private PaymentServiceContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                  final KafkaSslContainer kafkaContainer,
                                  final SchemaRegistryContainer schemaRegistryContainer,
                                  final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(PAYMENT_SERVICE_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainer,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
