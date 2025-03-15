package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.test.container.KafkaSslContainer;
import com.acroteq.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class CustomerMdmContainer extends AbstractTicketingContainer<CustomerMdmContainer> {

  private static final String CUSTOMER_MDM_IMAGE_NAME = "adamcc/ticketing/customer-mdm-container:0.1.0";

  private static final int EXPOSED_PORT = 8185;
  private static final String CONTAINER_NAME = "CustomerMdm";

  public static CustomerMdmContainer startCustomerMdmContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    final CustomerMdmContainer customerMdmContainer = new CustomerMdmContainer(postgreSqlContainer,
                                                                               kafkaContainers,
                                                                               schemaRegistryContainer,
                                                                               keycloakContainer);
    customerMdmContainer.start();

    return customerMdmContainer;
  }

  @SuppressWarnings("resource")
  private CustomerMdmContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(CUSTOMER_MDM_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainers,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
