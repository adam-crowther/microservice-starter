package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JdbcQueryExecutor implements AutoCloseable {

  private static final String COUNT_COLUMN = "rowCount";

  private final Connection connection;

  @SneakyThrows
  JdbcQueryExecutor(final Connection connection) {
    this.connection = connection;
  }

  int getCount(final String tableName) {
    return count(tableName);
  }

  void waitForCount(final String tableName, final int expectedCount) {
    Awaitility.await()
              .until(() -> count(tableName) == expectedCount);
  }

  void waitForEntityWithId(final String tableName, final Long id) {
    Awaitility.await()
              .until(() -> exists(tableName, id));
  }

  @SneakyThrows
  private int count(final String tableName) {
    final String query = createCountQuery(tableName);
    final int count;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      count = executeCountStatement(statement, tableName);
    }

    return count;
  }

  private String createCountQuery(final String tableName) {
    return String.format("SELECT count(*) AS %s FROM %s", COUNT_COLUMN, tableName);
  }

  @SneakyThrows
  private boolean exists(final String tableName, final Long id) {
    final String query = createExistsQuery(tableName);
    final int count;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, id);
      count = executeCountStatement(statement, tableName);
    }

    return count > 0;
  }

  private int executeCountStatement(final PreparedStatement statement, final String tableName) throws SQLException {
    final int count;
    try (ResultSet result = statement.executeQuery()) {
      if (result.next()) {
        count = result.getInt(COUNT_COLUMN);
        log.debug("Row count for table {}: {}", tableName, count);
      } else {
        count = 0;
      }
    }
    return count;
  }

  private String createExistsQuery(final String tableName) {
    return String.format("SELECT count(*) AS %s FROM %s where id = ?", COUNT_COLUMN, tableName);
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }
}
