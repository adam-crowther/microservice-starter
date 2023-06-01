package com.acroteq.ticketing.test.extension;

import static com.acroteq.ticketing.test.container.PostgreSqlContainer.POSTGRESQL_DATABASE_NAME;
import static com.acroteq.ticketing.test.container.PostgreSqlContainer.POSTGRESQL_PASSWORD;
import static com.acroteq.ticketing.test.container.PostgreSqlContainer.POSTGRESQL_USERNAME;

import com.acroteq.ticketing.test.container.PostgreSqlContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class PostgreSqlContainerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final PostgreSqlContainer postgres = new PostgreSqlContainer();

  @Override
  public void beforeAll(final ExtensionContext context) {
    postgres.start();

    final String mappedPostgresPort = postgres.getMappedPostgresPort()
                                              .toString();

    System.setProperty("DATASOURCE_HOST", "localhost");
    System.setProperty("DATASOURCE_PORT", mappedPostgresPort);
    System.setProperty("DATASOURCE_DATABASE", POSTGRESQL_DATABASE_NAME);
    System.setProperty("DATASOURCE_USERNAME", POSTGRESQL_USERNAME);
    System.setProperty("DATASOURCE_PASSWORD", POSTGRESQL_PASSWORD);
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    postgres.stop();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return parameterContext.getParameter()
                           .getType()
                           .equals(PostgreSQLContainer.class);
  }

  @Override
  public PostgreSQLContainer<?> resolveParameter(final ParameterContext parameterContext,
                                                 final ExtensionContext extensionContext) {
    return postgres;
  }
}