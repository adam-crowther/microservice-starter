package com.acroteq.ticketing.order.service.extension;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;
import static org.testcontainers.containers.output.OutputFrame.OutputType.STDERR;
import static org.testcontainers.containers.output.OutputFrame.OutputType.STDOUT;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.time.Duration;

@Slf4j
public class PostgreSqlContainerExtension implements BeforeAllCallback, AfterAllCallback {

  private static final String POSTGRES_DOCKER_IMAGE = "postgres:15.2-alpine";
  private static final String POSTGRES_DATABASE_NAME = "postgres";
  private static final String POSTGRES_USERNAME = "postgres";
  private static final String POSTGRES_PASSWORD = "password";
  private static final String POSTGRES_URL =
      "jdbc:postgresql://localhost:%d/postgres?currentSchema=orders&binaryTransfer=true&reWriteBatchedInserts"
      + "=true&stringType=unspecified";

  private static final String READY_TO_ACCEPT_CONNECTIONS = ".*database system is ready to accept connections.*\\s";

  @SuppressWarnings({ "resource", "PMD.CloseResource" })
  @Override
  public void beforeAll(final ExtensionContext context) {
    final WaitStrategy waitStrategy = new LogMessageWaitStrategy().withRegEx(READY_TO_ACCEPT_CONNECTIONS)
                                                                  .withTimes(1)
                                                                  .withStartupTimeout(Duration.of(60, SECONDS));
    final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRES_DOCKER_IMAGE).withDatabaseName(
                                                                                                POSTGRES_DATABASE_NAME)
                                                                                            .withUsername(
                                                                                                POSTGRES_USERNAME)
                                                                                            .withPassword(
                                                                                                POSTGRES_PASSWORD)
                                                                                            .withExposedPorts(
                                                                                                POSTGRESQL_PORT)
                                                                                            .withLogConsumer(this::logOutputFrame)
                                                                                            .withInitScript(
                                                                                                "init-schema.sql")
                                                                                            .waitingFor(waitStrategy);
    postgres.start();

    final String jdbcUrl = String.format(POSTGRES_URL, postgres.getFirstMappedPort());
    System.setProperty("spring.datasource.url", jdbcUrl);
    System.setProperty("spring.datasource.username", POSTGRES_USERNAME);
    System.setProperty("spring.datasource.password", POSTGRES_PASSWORD);
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    // do nothing, Testcontainers handles container shutdown
  }

  private void logOutputFrame(final OutputFrame outputFrame) {
    if (outputFrame.getType() == STDOUT) {
      log.info(outputFrame.getUtf8String());
    } else if (outputFrame.getType() == STDERR) {
      log.error(outputFrame.getUtf8String());
    }
  }
}