package com.acroteq.test.container;

import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkName;
import static org.testcontainers.containers.BindMode.READ_ONLY;

import com.acroteq.test.extension.HostNameSetter;
import com.acroteq.test.extension.OutputFrameLogger;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.stream.Collectors;

public class SchemaRegistryContainer extends GenericContainer<SchemaRegistryContainer> {

  private static final String SCHEMA_REGISTRY_IMAGE_NAME = "confluentinc/cp-schema-registry:7.9.0";

  private static final String CONTAINER_NAME = "SchemaRegistry";
  private static final String SECURITY_PROTOCOL = "SSL";
  private static final int LISTENER_PORT = 8081;
  private static final String ENV_HOST_NAME = "SCHEMA_REGISTRY_HOST_NAME";
  private static final String ENV_LISTENERS = "SCHEMA_REGISTRY_LISTENERS";
  private static final String ENV_BOOTSTRAP_SERVERS = "SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS";
  private static final String ENV_SECURITY_PROTOCOL = "SCHEMA_REGISTRY_KAFKASTORE_SECURITY_PROTOCOL";
  private static final String ENV_SSL_TRUSTSTORE_LOCATION = "SCHEMA_REGISTRY_KAFKASTORE_SSL_TRUSTSTORE_LOCATION";
  private static final String ENV_SSL_TRUSTSTORE_PASSWORD = "SCHEMA_REGISTRY_KAFKASTORE_SSL_TRUSTSTORE_PASSWORD";
  private static final String ENV_SSL_KEYSTORE_LOCATION = "SCHEMA_REGISTRY_KAFKASTORE_SSL_KEYSTORE_LOCATION";
  private static final String ENV_SSL_KEYSTORE_PASSWORD = "SCHEMA_REGISTRY_KAFKASTORE_SSL_KEYSTORE_PASSWORD";
  private static final String ENV_SSL_KEY_PASSWORD = "SCHEMA_REGISTRY_KAFKASTORE_SSL_KEY_PASSWORD";
  private static final String ENV_SSL_ENDPOINT_ID_ALGORITHM =
      "SCHEMA_REGISTRY_KAFKASTORE_SSL_ENDPOINT_IDENTIFICATION_ALGORITHM";
  private static final String ENV_TOPIC_REPLICATION_FACTOR = "SCHEMA_REGISTRY_KAFKASTORE_TOPIC_REPLICATION_FACTOR";
  private static final String ENV_DEBUG = "SCHEMA_REGISTRY_DEBUG";
  private static final String STARTER_SCRIPT = "/testcontainers_start.sh";
  private static final String PASSWORD = "acroteq";
  private static final String TRUSTSTORE_LOCATION = "/var/private/ssl/schema-registry.truststore.jks";
  private static final String KEYSTORE_LOCATION = "/var/private/ssl/schema-registry.keystore.jks";
  private static final String REPLICATION_FACTOR = "1";
  private static final String DEBUG_MODE = "true";
  private static final String SHEBANG = "#!/bin/bash";
  private static final String LINE_BREAK = "\n";

  @SuppressWarnings({ "OctalInteger", "PMD.AvoidUsingOctalValues" })
  private static final int FILE_MODE_0777 = 0777;

  public static SchemaRegistryContainer startSchemaRegistryContainer(final List<KafkaSslContainer> kafkaContainers) {
    final SchemaRegistryContainer schemaRegistryContainer = new SchemaRegistryContainer(kafkaContainers);
    schemaRegistryContainer.start();
    return schemaRegistryContainer;
  }

  @SuppressWarnings({"resource", "PMD.ConstructorCallsOverridableMethod"})
  public SchemaRegistryContainer(final List<KafkaSslContainer> kafkaContainers) {
    super(DockerImageName.parse(SCHEMA_REGISTRY_IMAGE_NAME));
    withClasspathResourceMapping("certs/schema-registry.keystore.jks",
                                 "/var/private/ssl/schema-registry.keystore.jks",
                                 READ_ONLY);
    withClasspathResourceMapping("certs/schema-registry.truststore.jks",
                                 "/var/private/ssl/schema-registry.truststore.jks",
                                 READ_ONLY);

    final OutputFrameLogger logConsumer = new OutputFrameLogger(CONTAINER_NAME);
    final HostNameSetter hostNameSetter = new HostNameSetter(CONTAINER_NAME);

    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());
    withLogConsumer(logConsumer);
    withCreateContainerCmdModifier(hostNameSetter);

    final String kafkaBootstrapServer = getKafkaBootstrapServers(kafkaContainers);
    withEnv(ENV_BOOTSTRAP_SERVERS, kafkaBootstrapServer);
    withEnv(ENV_SECURITY_PROTOCOL, SECURITY_PROTOCOL);
    withEnv(ENV_SSL_TRUSTSTORE_LOCATION, TRUSTSTORE_LOCATION);
    withEnv(ENV_SSL_TRUSTSTORE_PASSWORD, PASSWORD);
    withEnv(ENV_SSL_KEYSTORE_LOCATION, KEYSTORE_LOCATION);
    withEnv(ENV_SSL_KEYSTORE_PASSWORD, PASSWORD);
    withEnv(ENV_SSL_KEY_PASSWORD, PASSWORD);
    withEnv(ENV_SSL_ENDPOINT_ID_ALGORITHM, "");
    withEnv(ENV_TOPIC_REPLICATION_FACTOR, REPLICATION_FACTOR);
    withEnv(ENV_DEBUG, DEBUG_MODE);

    withExposedPorts(LISTENER_PORT);

    kafkaContainers.forEach(this::dependsOn);

    withCreateContainerCmdModifier(cmd -> {
      cmd.withEntrypoint("sh");
    });
    withCommand("-c", "while [ ! -f " + STARTER_SCRIPT + " ]; do sleep 0.1; done; " + STARTER_SCRIPT);
  }

  @Override
  protected void containerIsStarting(final InspectContainerResponse containerInfo) {
    super.containerIsStarting(containerInfo);

    final String hostName = containerInfo.getConfig()
                                         .getHostName();

    final String commands = SHEBANG
                            + LINE_BREAK
                            + LINE_BREAK
                            + exportHostNameCommand(hostName)
                            + LINE_BREAK
                            + exportListeners(hostName)
                            + LINE_BREAK
                            + "/etc/confluent/docker/run"
                            + LINE_BREAK;

    final Transferable file = Transferable.of(commands, FILE_MODE_0777);
    copyFileToContainer(file, STARTER_SCRIPT);
  }

  private String exportHostNameCommand(final String hostName) {
    return String.format("export %s=%s", ENV_HOST_NAME, hostName);
  }

  private String exportListeners(final String hostName) {
    return String.format("export %s=%s", ENV_LISTENERS, getListeners(hostName));
  }

  private String getListeners(final String hostName) {
    return String.format("http://%s:%d", hostName, LISTENER_PORT);
  }


  private String getKafkaBootstrapServers(final List<KafkaSslContainer> kafkaContainers) {
    return kafkaContainers.stream()
                          .map(this::getKafkaBootstrapServer)
                          .collect(Collectors.joining(","));
  }

  private String getKafkaBootstrapServer(final KafkaSslContainer kafkaContainer) {
    final String kafkaHostName = kafkaContainer.getContainerInfo()
                                               .getConfig()
                                               .getHostName();
    final Integer kafkaPort = kafkaContainer.getInternalSslBrokerPort();
    return String.format("SSL://%s:%d", kafkaHostName, kafkaPort);
  }

  public int getSchemaRegistryListenerPort() {
    return LISTENER_PORT;
  }
}
