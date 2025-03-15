package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.test.container.KafkaSslContainer;
import com.acroteq.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class AirlineApprovalContainer extends AbstractTicketingContainer<AirlineApprovalContainer> {

  private static final String AIRLINE_APPROVAL_IMAGE_NAME = "adamcc/ticketing/airline-approval-container:0.1.0";

  private static final int EXPOSED_PORT = 8184;
  private static final String CONTAINER_NAME = "AirlineApproval";

  public static AirlineApprovalContainer startAirlineApprovalContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    final AirlineApprovalContainer airlineApprovalContainer = new AirlineApprovalContainer(postgreSqlContainer,
                                                                                           kafkaContainers,
                                                                                           schemaRegistryContainer,
                                                                                           keycloakContainer);
    airlineApprovalContainer.start();

    return airlineApprovalContainer;
  }

  @SuppressWarnings("resource")
  private AirlineApprovalContainer(
      final PostgreSQLContainer<?> postgreSqlContainer, final List<KafkaSslContainer> kafkaContainers,
      final SchemaRegistryContainer schemaRegistryContainer, final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(AIRLINE_APPROVAL_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainers,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
