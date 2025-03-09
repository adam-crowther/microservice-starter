package com.acroteq.test.container;

import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkName;
import static org.testcontainers.containers.BindMode.READ_ONLY;

import com.acroteq.test.extension.HostNameSetter;
import com.acroteq.test.extension.OutputFrameLogger;
import com.github.dockerjava.api.command.InspectContainerResponse;
import lombok.EqualsAndHashCode;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@EqualsAndHashCode(callSuper = true)
public class KafkaSslContainer extends KafkaContainer {

  public static final String KAFKA_IMAGE_NAME = "confluentinc/cp-kafka:7.9.0";

  private static final String ENV_BROKER_ID = "KAFKA_BROKER_ID";
  private static final String ENV_OFFSETS_TOPIC_REPL_FACTOR = "KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR";
  private static final String ENV_DEFAULT_REPLICATION_FACTOR = "KAFKA_DEFAULT_REPLICATION_FACTOR";
  private static final String ENV_NUM_PARTITIONS = "KAFKA_NUM_PARTITIONS";
  private static final String ENV_COMPRESSION_TYPE = "KAFKA_COMPRESSION_TYPE";
  private static final String ENV_LISTENER_SECURITY_PROTOCOL_MAP = "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP";
  private static final String ENV_LISTENERS = "KAFKA_LISTENERS";
  private static final String ENV_SSL_CLIENT_AUTH = "KAFKA_SSL_CLIENT_AUTH";
  private static final String ENV_SSL_KEYSTORE_FILENAME = "KAFKA_SSL_KEYSTORE_FILENAME";
  private static final String ENV_SSL_KEYSTORE_CREDENTIALS = "KAFKA_SSL_KEYSTORE_CREDENTIALS";
  private static final String ENV_SSL_KEY_CREDENTIALS = "KAFKA_SSL_KEY_CREDENTIALS";
  private static final String ENV_SSL_TRUSTSTORE_FILENAME = "KAFKA_SSL_TRUSTSTORE_FILENAME";
  private static final String ENV_SSL_TRUSTSTORE_CREDENTIALS = "KAFKA_SSL_TRUSTSTORE_CREDENTIALS";
  private static final String ENV_SSL_ENDPOINT_ID_ALGORITHM = "KAFKA_SSL_ENDPOINT_IDENTIFICATION_ALGORITHM";

  private static final String KAFKA_CONTAINER_NAME = "Kafka";
  private static final int KAFKA_BROKER_PORT = 9090;
  private static final int KAFKA_INTERNAL_PLAINTEXT_PORT = 9092;
  private static final int KAFKA_INTERNAL_SSL_PORT = 9093;
  private static final int EXTERNAL_PORT_FACTOR = 10_000;

  private final int brokerId;

  public static KafkaSslContainer startKafkaSslContainer(final int brokerId) {
    final KafkaSslContainer kafkaContainer = new KafkaSslContainer(brokerId, 1);
    kafkaContainer.start();
    return kafkaContainer;
  }

  @SuppressWarnings({"resource", "PMD.ConstructorCallsOverridableMethod"})
  public KafkaSslContainer(final int brokerId, final int numberOfBrokers) {
    super(DockerImageName.parse(KAFKA_IMAGE_NAME));
    this.brokerId = brokerId;

    final String brokerIdStr = Integer.toString(brokerId);
    final String containerName = KAFKA_CONTAINER_NAME + brokerIdStr;
    final OutputFrameLogger logConsumer = new OutputFrameLogger(containerName);
    withLogConsumer(logConsumer);

    final HostNameSetter hostNameSetter = new HostNameSetter(containerName);
    withCreateContainerCmdModifier(hostNameSetter);

    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());

    final int exposedPlaintextPort = getExposedPlaintextPort();
    final int exposedSslPort = getExposedSslPort();
    withExposedPorts(exposedPlaintextPort, exposedSslPort);
    withClasspathResourceMapping("certs/kafka-broker.keystore.jks",
                                 "/etc/kafka/secrets/certs/kafka-broker.keystore.jks",
                                 READ_ONLY);
    withClasspathResourceMapping("certs/kafka-broker_keystore_creds",
                                 "/etc/kafka/secrets/certs/kafka-broker_keystore_creds",
                                 READ_ONLY);
    withClasspathResourceMapping("certs/kafka-broker_sslkey_creds",
                                 "/etc/kafka/secrets/certs/kafka-broker_sslkey_creds",
                                 READ_ONLY);
    withClasspathResourceMapping("certs/kafka-broker.truststore.jks",
                                 "/etc/kafka/secrets/certs/kafka-broker.truststore.jks",
                                 READ_ONLY);
    withClasspathResourceMapping("certs/kafka-broker_truststore_creds",
                                 "/etc/kafka/secrets/certs/kafka-broker_truststore_creds",
                                 READ_ONLY);

    final String replicationFactor = getReplicationFactor(numberOfBrokers);
    withEnv(ENV_BROKER_ID, brokerIdStr);
    withEnv(ENV_OFFSETS_TOPIC_REPL_FACTOR, replicationFactor);
    withEnv(ENV_DEFAULT_REPLICATION_FACTOR, replicationFactor);
    final String numberOfPartitions = getNumberOfPartitions(numberOfBrokers);
    withEnv(ENV_NUM_PARTITIONS, numberOfPartitions);
    withEnv(ENV_COMPRESSION_TYPE, "producer");
    withEnv(ENV_LISTENERS, getListeners());
    withEnv(ENV_LISTENER_SECURITY_PROTOCOL_MAP,
            "BROKER:PLAINTEXT,INTERNAL_PLAINTEXT:PLAINTEXT,EXPOSED_PLAINTEXT:PLAINTEXT,INTERNAL_SSL:SSL,"
            + "EXPOSED_SSL:SSL");
    withEnv(ENV_SSL_CLIENT_AUTH, "required");
    withEnv(ENV_SSL_KEYSTORE_FILENAME, "certs/kafka-broker.keystore.jks");
    withEnv(ENV_SSL_KEYSTORE_CREDENTIALS, "certs/kafka-broker_keystore_creds");
    withEnv(ENV_SSL_KEY_CREDENTIALS, "certs/kafka-broker_sslkey_creds");
    withEnv(ENV_SSL_TRUSTSTORE_FILENAME, "certs/kafka-broker.truststore.jks");
    withEnv(ENV_SSL_TRUSTSTORE_CREDENTIALS, "certs/kafka-broker_truststore_creds");
    withEnv(ENV_SSL_ENDPOINT_ID_ALGORITHM, "");
  }

  private String getReplicationFactor(final int numberOfBrokers) {
    final int replicationFactor = numberOfBrokers > 1 ? 2 : 1;
    return Integer.toString(replicationFactor);
  }

  private String getNumberOfPartitions(final int numberOfBrokers) {
    final int numberOfPartitions = numberOfBrokers * 3;
    return Integer.toString(numberOfPartitions);
  }

  private String getListeners() {
    final int exposedPlaintextPort = getExposedPlaintextPort();
    final int exposedSslPort = getExposedSslPort();
    return "BROKER://0.0.0.0:" + KAFKA_BROKER_PORT + ","
           + "INTERNAL_PLAINTEXT://0.0.0.0:" + KAFKA_INTERNAL_PLAINTEXT_PORT + ","
           + "INTERNAL_SSL://0.0.0.0:" + KAFKA_INTERNAL_SSL_PORT + ","
           + "EXPOSED_PLAINTEXT://0.0.0.0:" + exposedPlaintextPort + ","
           + "EXPOSED_SSL://0.0.0.0:" + exposedSslPort;
  }

  @Override
  public String getBootstrapServers() {
    final int exposedPlaintextPort = getExposedPlaintextPort();
    final int mappedPlaintextPort = getMappedPort(exposedPlaintextPort);
    final int exposedSslPort = getExposedSslPort();
    final int mappedSslPort = getMappedPort(exposedSslPort);
    final String hostName = getContainerInfo().getConfig()
                                              .getHostName();
    return String.format("INTERNAL_PLAINTEXT://%s:%d", hostName, KAFKA_INTERNAL_PLAINTEXT_PORT) + ","
           + String.format("INTERNAL_SSL://%s:%d", hostName, KAFKA_INTERNAL_SSL_PORT) + ","
           + String.format("EXPOSED_PLAINTEXT://localhost:%d", mappedPlaintextPort) + ","
           + String.format("EXPOSED_SSL://localhost:%d", mappedSslPort);
  }

  @Override
  protected String brokerAdvertisedListener(final InspectContainerResponse containerInfo) {
    return String.format("BROKER://%s:%s",
                         containerInfo.getConfig()
                                      .getHostName(),
                         KAFKA_BROKER_PORT);
  }

  public final int getInternalPlaintextBrokerPort() {
    return KAFKA_INTERNAL_PLAINTEXT_PORT;
  }

  public final int getInternalSslBrokerPort() {
    return KAFKA_INTERNAL_SSL_PORT;
  }

  public final int getMappedExposedPlaintextBrokerPort() {
    final int exposedPlaintextPort = getExposedPlaintextPort();
    return getMappedPort(exposedPlaintextPort);
  }

  public final int getMappedExposedSslBrokerPort() {
    final int exposedSslPort = getExposedSslPort();
    return getMappedPort(exposedSslPort);
  }

  public final int getExposedPlaintextPort() {
    return brokerId * EXTERNAL_PORT_FACTOR + KAFKA_INTERNAL_PLAINTEXT_PORT;
  }

  public final int getExposedSslPort() {
    return brokerId * EXTERNAL_PORT_FACTOR + KAFKA_INTERNAL_SSL_PORT;
  }
}
