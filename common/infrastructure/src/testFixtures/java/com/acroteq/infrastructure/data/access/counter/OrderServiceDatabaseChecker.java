package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class OrderServiceDatabaseChecker implements AutoCloseable {

  private static final String SCHEMA = "orders";
  private static final String AIRLINES = "airlines";
  private static final String FLIGHTS = "flights";
  private static final String CUSTOMERS = "customers";
  private static final String ORDERS = "orders";
  private final JdbcQueryExecutor queryExecutor;

  public OrderServiceDatabaseChecker(final JdbcQueryExecutor queryExecutor) {
    this.queryExecutor = queryExecutor;
  }

  public void waitForCustomer(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + CUSTOMERS, id);
  }

  public void waitForCustomersCount(final int count) {
    queryExecutor.waitForCount(SCHEMA + "." + CUSTOMERS, count);
  }

  public void waitForAirline(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + AIRLINES, id);
  }

  public void waitForFlight(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + FLIGHTS, id);
  }

  public int getOrderCount() {
    return queryExecutor.getCount(SCHEMA + "." + ORDERS);
  }

  public void waitForOrdersCount(final int count) {
    queryExecutor.waitForCount(SCHEMA + "." + ORDERS, count);
  }

  @SneakyThrows
  @Override
  public void close() {
    queryExecutor.close();
  }
}
