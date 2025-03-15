package com.acroteq.ticketing.test.e2e.api;

import static com.acroteq.test.container.PostgreSqlContainer.POSTGRESQL_DATABASE_NAME;
import static com.acroteq.test.container.PostgreSqlContainer.POSTGRESQL_PASSWORD;
import static com.acroteq.test.container.PostgreSqlContainer.POSTGRESQL_USERNAME;

import com.acroteq.infrastructure.data.access.counter.JdbcDatabaseChecker;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class DatabaseCheckerFactory {

  @SuppressFBWarnings("HARD_CODE_PASSWORD")
  public static JdbcDatabaseChecker createDatabaseChecker(final TestDockerContainers containers) {
    final String host = containers.getPostgreSqlHost();
    final Integer port = containers.getPostgreSqlPort();

    return JdbcDatabaseChecker.builder()
                              .host(host)
                              .port(port)
                              .database(POSTGRESQL_DATABASE_NAME)
                              .user(POSTGRESQL_USERNAME)
                              .password(POSTGRESQL_PASSWORD)
                              .build();
  }

  private DatabaseCheckerFactory() {
  }
}
