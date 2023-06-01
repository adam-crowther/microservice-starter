package com.acroteq.ticketing.test.container;

import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkName;
import static org.testcontainers.containers.BindMode.READ_ONLY;

import com.acroteq.ticketing.test.extension.HostNameSetter;
import com.acroteq.ticketing.test.extension.OutputFrameLogger;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaSslContainer extends KafkaContainer {

  private static final String KAFKA_IMAGE_NAME = "confluentinc/cp-kafka:7.3.2";

  private static final String ENV_BROKER_ID = "KAFKA_BROKER_ID";
  private static final String ENV_OFFSETS_TOPIC_REPL_FACTOR = "KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR";
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
  private static final int KAFKA_EXPOSED_PLAINTEXT_PORT = 19092;
  private static final int KAFKA_EXPOSED_SSL_PORT = 19093;

  public static KafkaSslContainer startKafkaSslContainer() {
    final KafkaSslContainer kafkaContainer = new KafkaSslContainer();
    kafkaContainer.start();
    return kafkaContainer;
  }

  public KafkaSslContainer() {
    this(DockerImageName.parse(KAFKA_IMAGE_NAME));
  }

  @SuppressWarnings("resource")
  private KafkaSslContainer(final DockerImageName dockerImageName) {
    super(dockerImageName);

    withKraft();

    final OutputFrameLogger logConsumer = new OutputFrameLogger(KAFKA_CONTAINER_NAME);
    withLogConsumer(logConsumer);

    final HostNameSetter hostNameSetter = new HostNameSetter(KAFKA_CONTAINER_NAME);
    withCreateContainerCmdModifier(hostNameSetter);

    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());

    withExposedPorts(KAFKA_EXPOSED_PLAINTEXT_PORT,
                     KAFKA_EXPOSED_SSL_PORT);
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

    withEnv(ENV_BROKER_ID, "1");
    withEnv(ENV_OFFSETS_TOPIC_REPL_FACTOR, "1");
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

  private String getListeners() {
    return "BROKER://0.0.0.0:" + KAFKA_BROKER_PORT + ","
           + "INTERNAL_PLAINTEXT://0.0.0.0:" + KAFKA_INTERNAL_PLAINTEXT_PORT + ","
           + "INTERNAL_SSL://0.0.0.0:" + KAFKA_INTERNAL_SSL_PORT + ","
           + "EXPOSED_PLAINTEXT://0.0.0.0:" + KAFKA_EXPOSED_PLAINTEXT_PORT + ","
           + "EXPOSED_SSL://0.0.0.0:" + KAFKA_EXPOSED_SSL_PORT;
  }

  @Override
  public String getBootstrapServers() {
    final int mappedPlaintextPort = getMappedPort(KAFKA_EXPOSED_PLAINTEXT_PORT);
    final int mappedSslPort = getMappedPort(KAFKA_EXPOSED_SSL_PORT);
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

  public int getInternalPlaintextBrokerPort() {
    return KAFKA_INTERNAL_PLAINTEXT_PORT;
  }

  public int getInternalSslBrokerPort() {
    return KAFKA_INTERNAL_SSL_PORT;
  }

  public int getMappedExposedPlaintextBrokerPort() {
    return getMappedPort(KAFKA_EXPOSED_PLAINTEXT_PORT);
  }

  public int getMappedExposedSslBrokerPort() {
    return getMappedPort(KAFKA_EXPOSED_SSL_PORT);
  }
}
