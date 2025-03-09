package com.acroteq.ticketing.infrastructure.data.access.counter;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class JdbcDatabaseCheckerBuilder {

  private static final String SCHEMA_AIRLINE_MASTER = "airline_master";
  private static final String SCHEMA_CUSTOMER_MASTER = "customer_master";
  private static final String SCHEMA_AIRLINE_APPROVAL = "airline_approval";
  private static final String SCHEMA_ORDERS = "orders";
  private static final String SCHEMA_PAYMENT = "payment";

  private String host;
  private int port;
  private String database;
  private String user;
  private String password;

  public JdbcDatabaseCheckerBuilder host(final String host) {
    this.host = host;
    return this;
  }

  public JdbcDatabaseCheckerBuilder port(final int port) {
    this.port = port;
    return this;
  }

  public JdbcDatabaseCheckerBuilder database(final String database) {
    this.database = database;
    return this;
  }

  public JdbcDatabaseCheckerBuilder user(final String user) {
    this.user = user;
    return this;
  }

  public JdbcDatabaseCheckerBuilder password(final String password) {
    this.password = password;
    return this;
  }

  @SuppressWarnings("PMD.CloseResource")
  @SneakyThrows
  public JdbcDatabaseChecker build() {
    final JdbcQueryExecutor airlineMdmQueryExecutor = createExecutor(SCHEMA_AIRLINE_MASTER);
    final JdbcQueryExecutor customerMdmQueryExecutor = createExecutor(SCHEMA_CUSTOMER_MASTER);
    final JdbcQueryExecutor airlineApprovalQueryExecutor = createExecutor(SCHEMA_AIRLINE_APPROVAL);
    final JdbcQueryExecutor orderServiceQueryExecutor = createExecutor(SCHEMA_ORDERS);
    final JdbcQueryExecutor paymentServiceQueryExecutor = createExecutor(SCHEMA_PAYMENT);

    return new JdbcDatabaseChecker(airlineMdmQueryExecutor,
                                   customerMdmQueryExecutor,
                                   airlineApprovalQueryExecutor,
                                   orderServiceQueryExecutor,
                                   paymentServiceQueryExecutor);
  }

  private JdbcQueryExecutor createExecutor(final String schema) throws SQLException {
    final String url = String.format("jdbc:postgresql://%s:%d/%s?currentSchema=%s", host, port, database, schema);
    final Connection connection = DriverManager.getConnection(url, user, password);
    return new JdbcQueryExecutor(connection);
  }
}
