package com.acroteq.ticketing.test.container;

import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkName;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.acroteq.ticketing.test.extension.HostNameSetter;
import com.acroteq.ticketing.test.extension.OutputFrameLogger;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class PostgreSqlContainer extends PostgreSQLContainer<PostgreSqlContainer> {

  private static final String POSTGRESQL_DOCKER_IMAGE = "postgres:15.2-alpine";

  public static final String POSTGRESQL_DATABASE_NAME = "postgres";
  public static final String POSTGRESQL_USERNAME = "postgres";
  public static final String POSTGRESQL_PASSWORD = "password";

  private static final String READY_TO_ACCEPT_CONNECTIONS = ".*database system is ready to accept connections.*\\s";
  private static final String POSTGRESQL_INIT_SQL = "init-schema.sql";
  private static final String POSTGRESQL_CONTAINER_NAME = "PostgreSql";

  public static PostgreSqlContainer startPostgreSqlContainer() {
    final PostgreSqlContainer kafkaContainer = new PostgreSqlContainer();
    kafkaContainer.start();
    return kafkaContainer;
  }

  @SuppressWarnings("resource")
  public PostgreSqlContainer() {
    super(DockerImageName.parse(POSTGRESQL_DOCKER_IMAGE));

    final OutputFrameLogger logConsumer = new OutputFrameLogger(POSTGRESQL_CONTAINER_NAME);
    final HostNameSetter hostNameSetter = new HostNameSetter(POSTGRESQL_CONTAINER_NAME);

    withDatabaseName(POSTGRESQL_DATABASE_NAME);
    withUsername(POSTGRESQL_USERNAME);
    withPassword(POSTGRESQL_PASSWORD);
    withExposedPorts(POSTGRESQL_PORT);
    withInitScript(POSTGRESQL_INIT_SQL);
    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());
    withCreateContainerCmdModifier(hostNameSetter);
    withLogConsumer(logConsumer);

    final WaitStrategy waitStrategy = new LogMessageWaitStrategy().withRegEx(READY_TO_ACCEPT_CONNECTIONS)
                                                                  .withTimes(1)
                                                                  .withStartupTimeout(Duration.of(60, SECONDS));
    waitingFor(waitStrategy);
  }

  public Integer getMappedPostgresPort() {
    return getMappedPort(POSTGRESQL_PORT);
  }
}
