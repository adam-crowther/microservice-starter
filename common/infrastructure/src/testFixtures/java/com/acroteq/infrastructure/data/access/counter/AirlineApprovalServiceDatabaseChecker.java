package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class AirlineApprovalServiceDatabaseChecker implements AutoCloseable {

  private static final String SCHEMA = "airline_approval";
  private static final String AIRLINES = "airlines";
  private static final String FLIGHTS = "flights";

  private final JdbcQueryExecutor queryExecutor;

  public AirlineApprovalServiceDatabaseChecker(final JdbcQueryExecutor queryExecutor) {
    this.queryExecutor = queryExecutor;
  }

  public void waitForAirline(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + AIRLINES, id);
  }

  public void waitForFlight(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + FLIGHTS, id);
  }

  @SneakyThrows
  @Override
  public void close() {
    queryExecutor.close();
  }
}
