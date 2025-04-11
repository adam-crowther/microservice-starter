package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class CustomerMdmDatabaseChecker implements AutoCloseable {

  private static final String SCHEMA = "customer_master";
  private static final String CUSTOMERS = "customers";

  private final JdbcQueryExecutor queryExecutor;

  public CustomerMdmDatabaseChecker(final JdbcQueryExecutor queryExecutor) {
    this.queryExecutor = queryExecutor;
  }

  public void waitForCustomer(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + CUSTOMERS, id);
  }

  public void waitForCustomersCount(final int count) {
    queryExecutor.waitForCount(SCHEMA + "." + CUSTOMERS, count);
  }

  public int getCustomerCount() {
    return queryExecutor.getCount(SCHEMA + "." + CUSTOMERS);
  }

  @SneakyThrows
  @Override
  public void close() {
    queryExecutor.close();
  }
}
