package com.acroteq.ticketing.infrastructure.data.access.counter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class JdbcQueryExecutor implements AutoCloseable {

  private static final String COUNT_COLUMN = "rowCount";

  private final Connection connection;

  @SneakyThrows
  JdbcQueryExecutor(final Connection connection) {
    this.connection = connection;
  }

  void waitForCount(final String tableName, final int expectedCount) {
    Awaitility.await()
              .until(() -> count(tableName) == expectedCount);
  }

  @SuppressFBWarnings("SQL_INJECTION_JDBC")
  @SneakyThrows
  private int count(final String tableName) {
    final String query = createCountQuery(tableName);
    final int count;
    try (Statement statement = connection.createStatement();
         ResultSet result = statement.executeQuery(query)) {
      if (result.next()) {
        count = result.getInt(COUNT_COLUMN);
        log.debug("Row count for table {}: {}", tableName, count);
      } else {
        count = 0;
      }
    }

    return count;
  }

  private String createCountQuery(final String tableName) {
    return String.format("SELECT count(*) AS %s FROM %s", COUNT_COLUMN, tableName);
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }
}
