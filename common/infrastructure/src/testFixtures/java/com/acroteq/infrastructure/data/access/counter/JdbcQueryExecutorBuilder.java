package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class JdbcQueryExecutorBuilder {

  private String host;
  private int port;
  private String database;
  private String user;
  private String password;

  public JdbcQueryExecutorBuilder host(final String host) {
    this.host = host;
    return this;
  }

  public JdbcQueryExecutorBuilder port(final int port) {
    this.port = port;
    return this;
  }

  public JdbcQueryExecutorBuilder database(final String database) {
    this.database = database;
    return this;
  }

  public JdbcQueryExecutorBuilder user(final String user) {
    this.user = user;
    return this;
  }

  public JdbcQueryExecutorBuilder password(final String password) {
    this.password = password;
    return this;
  }

  @SuppressWarnings("PMD.CloseResource")
  @SneakyThrows
  public JdbcQueryExecutor build() {
    final String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
    final Connection connection = DriverManager.getConnection(url, user, password);
    return new JdbcQueryExecutor(connection);
  }
}
