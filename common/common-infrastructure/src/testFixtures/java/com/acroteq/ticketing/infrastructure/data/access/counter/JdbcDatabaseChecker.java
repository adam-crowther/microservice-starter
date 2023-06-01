package com.acroteq.ticketing.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class JdbcDatabaseChecker implements AutoCloseable {

  private static final String CUSTOMERS_TABLE = "customers";
  private static final String AIRLINES_TABLE = "airlines";
  private static final String ORDERS_TABLE = "orders";
  private static final String PAYMENTS_TABLE = "payments";
  
  private final JdbcQueryExecutor airlineMdmQueryExecutor;
  private final JdbcQueryExecutor customerMdmQueryExecutor;
  private final JdbcQueryExecutor airlineApprovalQueryExecutor;
  private final JdbcQueryExecutor orderServiceQueryExecutor;
  private final JdbcQueryExecutor paymentServiceQueryExecutor;

  public static JdbcDatabaseCheckerBuilder builder() {
    return new JdbcDatabaseCheckerBuilder();
  }

  JdbcDatabaseChecker(final JdbcQueryExecutor airlineMdmQueryExecutor,
                      final JdbcQueryExecutor customerMdmQueryExecutor,
                      final JdbcQueryExecutor airlineApprovalQueryExecutor,
                      final JdbcQueryExecutor orderServiceQueryExecutor,
                      final JdbcQueryExecutor paymentServiceQueryExecutor) {
    this.airlineMdmQueryExecutor = airlineMdmQueryExecutor;
    this.customerMdmQueryExecutor = customerMdmQueryExecutor;
    this.airlineApprovalQueryExecutor = airlineApprovalQueryExecutor;
    this.orderServiceQueryExecutor = orderServiceQueryExecutor;
    this.paymentServiceQueryExecutor = paymentServiceQueryExecutor;
  }

  public void inCustomerMdmWaitForCustomerCount(final int count) {
    customerMdmQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public void inOrderServiceWaitForCustomerCount(final int count) {
    orderServiceQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public void inPaymentServiceWaitForCustomerCount(final int count) {
    paymentServiceQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public void inAirlineMdmWaitForAirlineCount(final int count) {
    airlineMdmQueryExecutor.waitForCount(AIRLINES_TABLE, count);
  }

  public void inAirlineApprovalWaitForAirlineCount(final int count) {
    airlineApprovalQueryExecutor.waitForCount(AIRLINES_TABLE, count);
  }

  public void inOrderServiceWaitForAirlineCount(final int count) {
    orderServiceQueryExecutor.waitForCount(AIRLINES_TABLE, count);
  }

  public void inOrderServiceWaitForOrdersCount(final int count) {
    orderServiceQueryExecutor.waitForCount(ORDERS_TABLE, count);
  }

  public void inPaymentServiceWaitForPaymentsCount(final int count) {
    paymentServiceQueryExecutor.waitForCount(PAYMENTS_TABLE, count);
  }

  @SneakyThrows
  @Override
  public void close() {
    airlineMdmQueryExecutor.close();
    customerMdmQueryExecutor.close();
    airlineApprovalQueryExecutor.close();
    orderServiceQueryExecutor.close();
    paymentServiceQueryExecutor.close();
  }
}
