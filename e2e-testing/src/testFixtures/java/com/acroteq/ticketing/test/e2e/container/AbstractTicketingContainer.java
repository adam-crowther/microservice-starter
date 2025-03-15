package com.acroteq.ticketing.test.e2e.container;

import com.acroteq.test.container.KafkaSslContainer;
import com.acroteq.test.container.SchemaRegistryContainer;
import com.acroteq.test.extension.DockerNetworkSingleton;
import com.acroteq.test.extension.HostNameSetter;
import com.acroteq.test.extension.OutputFrameLogger;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.stream.Collectors;

abstract class AbstractTicketingContainer<SelfT extends GenericContainer<SelfT>> extends GenericContainer<SelfT> {


  private static final String ENV_DATASOURCE_HOST = "DATASOURCE_HOST";
  private static final String ENV_DATASOURCE_PORT = "DATASOURCE_PORT";
  private static final String ENV_DATASOURCE_DATABASE = "DATASOURCE_DATABASE";
  private static final String ENV_DATASOURCE_USERNAME = "DATASOURCE_USERNAME";
  private static final String ENV_DATASOURCE_PASSWORD = "DATASOURCE_PASSWORD";
  private static final String ENV_KEYCLOAK_HOST = "KEYCLOAK_HOST";
  private static final String ENV_KEYCLOAK_PORT = "KEYCLOAK_PORT";
  private static final String ENV_KAFKA_BOOTSTRAP_SERVERS = "KAFKA_BOOTSTRAP_SERVERS";
  private static final String ENV_SCHEMA_REGISTRY_URL = "SCHEMA_REGISTRY_URL";

  private static final String KEYCLOAK_PORT = "8080";

  private static final String POSTGRES_PORT = PostgreSQLContainer.POSTGRESQL_PORT.toString();

  @SuppressWarnings({ "resource", "PMD.ConstructorCallsOverridableMethod" })
  AbstractTicketingContainer(
      final DockerImageName dockerImageName, final PostgreSQLContainer<?> postgreSqlContainer,
      final List<KafkaSslContainer> kafkaContainers, final SchemaRegistryContainer schemaRegistryContainer,
      final KeycloakContainer keycloakContainer, final String containerName) {
    super(dockerImageName);

    final String postgresHost = getPostgresHost(postgreSqlContainer);
    final String kafkaBootstrapServers = getKafkaBootstrapServers(kafkaContainers);
    final String keycloakHost = getKeycloakHost(keycloakContainer);
    final String schemaRegistryUrl = getSchemaRegistryUrl(schemaRegistryContainer);

    final OutputFrameLogger logConsumer = new OutputFrameLogger(containerName);
    final HostNameSetter hostNameSetter = new HostNameSetter(containerName);

    withNetwork(DockerNetworkSingleton.getNetworkInstance());

    withNetworkAliases(DockerNetworkSingleton.getNetworkName());
    withLogConsumer(logConsumer);
    withCreateContainerCmdModifier(hostNameSetter);
    withEnv(ENV_DATASOURCE_HOST, postgresHost);
    withEnv(ENV_DATASOURCE_PORT, POSTGRES_PORT);
    withEnv(ENV_DATASOURCE_DATABASE, postgreSqlContainer.getDatabaseName());
    withEnv(ENV_DATASOURCE_USERNAME, postgreSqlContainer.getUsername());
    withEnv(ENV_DATASOURCE_PASSWORD, postgreSqlContainer.getPassword());
    withEnv(ENV_KEYCLOAK_HOST, keycloakHost);
    withEnv(ENV_KEYCLOAK_PORT, KEYCLOAK_PORT);
    withEnv(ENV_KAFKA_BOOTSTRAP_SERVERS, kafkaBootstrapServers);
    withEnv(ENV_SCHEMA_REGISTRY_URL, schemaRegistryUrl);

    dependsOn(postgreSqlContainer);
    kafkaContainers.forEach(super::dependsOn);
    dependsOn(schemaRegistryContainer);
    dependsOn(keycloakContainer);

    waitingFor(Wait.forLogMessage(".*Started \\S+ in [0-9\\.]+ seconds.*", 1));
  }

  private String getKeycloakHost(final KeycloakContainer keycloakContainer) {
    return keycloakContainer.getContainerInfo()
                            .getConfig()
                            .getHostName();
  }

  private String getSchemaRegistryUrl(final SchemaRegistryContainer schemaRegistryContainer) {
    final String schemaRegistryHost = schemaRegistryContainer.getContainerInfo()
                                                             .getConfig()
                                                             .getHostName();
    final int schemaRegistryPort = schemaRegistryContainer.getSchemaRegistryListenerPort();
    return String.format("http://%s:%d", schemaRegistryHost, schemaRegistryPort);
  }

  private String getPostgresHost(final PostgreSQLContainer<?> postgreSqlContainer) {
    return postgreSqlContainer.getContainerInfo()
                              .getConfig()
                              .getHostName();
  }

  private String getKafkaBootstrapServers(final List<KafkaSslContainer> kafkaContainers) {
    return kafkaContainers.stream()
                          .map(this::getKafkaBootstrapServer)
                          .collect(Collectors.joining(","));
  }

  private String getKafkaBootstrapServer(final KafkaSslContainer kafkaContainer) {
    final String kafkaHost = kafkaContainer.getContainerInfo()
                                           .getConfig()
                                           .getHostName();
    final int kafkaPort = kafkaContainer.getInternalSslBrokerPort();
    return String.format("%s:%d", kafkaHost, kafkaPort);
  }
}
