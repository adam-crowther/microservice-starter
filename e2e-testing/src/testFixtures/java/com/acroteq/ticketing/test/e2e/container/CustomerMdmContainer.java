package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public final class CustomerMdmContainer extends AbstractTicketingContainer<CustomerMdmContainer> {

  private static final String CUSTOMER_MDM_IMAGE_NAME = "adamcc/ticketing/customer-mdm-container:0.1.0";

  private static final int EXPOSED_PORT = 8185;
  private static final String CONTAINER_NAME = "CustomerMdm";

  public static CustomerMdmContainer startCustomerMdmContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                                               final KafkaSslContainer kafkaContainer,
                                                               final SchemaRegistryContainer schemaRegistryContainer,
                                                               final KeycloakContainer keycloakContainer) {
    final CustomerMdmContainer customerMdmContainer = new CustomerMdmContainer(postgreSqlContainer,
                                                                               kafkaContainer,
                                                                               schemaRegistryContainer,
                                                                               keycloakContainer);
    customerMdmContainer.start();

    return customerMdmContainer;
  }

  @SuppressWarnings("resource")
  private CustomerMdmContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                               final KafkaSslContainer kafkaContainer,
                               final SchemaRegistryContainer schemaRegistryContainer,
                               final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(CUSTOMER_MDM_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainer,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
