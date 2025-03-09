package com.acroteq.ticketing.infrastructure.data.access.counter;

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
    final String query = "SELECT count(*) AS ? FROM ?";
    final int count;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, COUNT_COLUMN);
      statement.setString(2, tableName);
      count = executeCountStatement(statement, tableName);
    }

    return count;
  }

  @SneakyThrows
  private boolean exists(final String tableName, final Long id) {
    final String query = "SELECT count(*) AS ? FROM ? where id = ?";

    final int count;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, COUNT_COLUMN);
      statement.setString(2, tableName);
      statement.setLong(3, id);
      count = executeCountStatement(statement, tableName);
    }

    return count > 0;
  }

  private int executeCountStatement(final PreparedStatement statement, final String tableName) throws SQLException {
    final int count;
    try (ResultSet result = statement.getResultSet()) {
      if (result.next()) {
        count = result.getInt(COUNT_COLUMN);
        log.debug("Row count for table {}: {}", tableName, count);
      } else {
        count = 0;
      }
    }
    return count;
  }

  @Override
  public void close() throws Exception {
    connection.close();
  }
}
