package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.SchemaRegistryContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class AirlineMdmContainer extends AbstractTicketingContainer<AirlineMdmContainer> {

  private static final String AIRLINE_MDM_IMAGE_NAME = "adamcc/ticketing/airline-mdm-container:0.1.0";

  private static final int EXPOSED_PORT = 8183;
  private static final String CONTAINER_NAME = "AirlineMdm";

  public static AirlineMdmContainer startAirlineMdmContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                                                             final List<KafkaSslContainer> kafkaContainers,
                                                             final SchemaRegistryContainer schemaRegistryContainer,
                                                             final KeycloakContainer keycloakContainer) {
    final AirlineMdmContainer airlineMdmContainer = new AirlineMdmContainer(postgreSqlContainer,
                                                                            kafkaContainers,
                                                                            schemaRegistryContainer,
                                                                            keycloakContainer);
    airlineMdmContainer.start();

    return airlineMdmContainer;
  }

  @SuppressWarnings("resource")
  private AirlineMdmContainer(final PostgreSQLContainer<?> postgreSqlContainer,
                              final List<KafkaSslContainer> kafkaContainers,
                              final SchemaRegistryContainer schemaRegistryContainer,
                              final KeycloakContainer keycloakContainer) {
    super(DockerImageName.parse(AIRLINE_MDM_IMAGE_NAME),
          postgreSqlContainer,
          kafkaContainers,
          schemaRegistryContainer,
          keycloakContainer,
          CONTAINER_NAME);

    withExposedPorts(EXPOSED_PORT);
  }
}
