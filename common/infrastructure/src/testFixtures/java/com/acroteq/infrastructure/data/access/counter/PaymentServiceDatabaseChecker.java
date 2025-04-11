package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class PaymentServiceDatabaseChecker implements AutoCloseable {

  private static final String SCHEMA = "payment";
  private static final String CUSTOMERS = "customers";
  private static final String PAYMENTS = "payments";

  private final JdbcQueryExecutor queryExecutor;

  public PaymentServiceDatabaseChecker(final JdbcQueryExecutor queryExecutor) {
    this.queryExecutor = queryExecutor;
  }

  public void waitForCustomer(final Long id) {
    queryExecutor.waitForEntityWithId(SCHEMA + "." + CUSTOMERS, id);
  }

  public void waitForCustomersCount(final int count) {
    queryExecutor.waitForCount(SCHEMA + "." + CUSTOMERS, count);
  }

  public void waitForPaymentsCount(final int count) {
    queryExecutor.waitForCount(SCHEMA + "." + PAYMENTS, count);
  }

  @SneakyThrows
  @Override
  public void close() {
    queryExecutor.close();
    queryExecutor.close();
    queryExecutor.close();
    queryExecutor.close();
    queryExecutor.close();
  }
}
