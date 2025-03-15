package com.acroteq.infrastructure.data.access.counter;

import lombok.SneakyThrows;

public class JdbcDatabaseChecker implements AutoCloseable {

  private static final String CUSTOMERS_TABLE = "customer_master.customers";
  private static final String AIRLINES_TABLE = "airline_master.airlines";
  private static final String ORDERS_TABLE = "orders.orders";
  private static final String PAYMENTS_TABLE = "payment.payments";

  private final JdbcQueryExecutor airlineMdmQueryExecutor;
  private final JdbcQueryExecutor customerMdmQueryExecutor;
  private final JdbcQueryExecutor airlineApprovalQueryExecutor;
  private final JdbcQueryExecutor orderServiceQueryExecutor;
  private final JdbcQueryExecutor paymentServiceQueryExecutor;

  public static JdbcDatabaseCheckerBuilder builder() {
    return new JdbcDatabaseCheckerBuilder();
  }

  JdbcDatabaseChecker(
      final JdbcQueryExecutor airlineMdmQueryExecutor, final JdbcQueryExecutor customerMdmQueryExecutor,
      final JdbcQueryExecutor airlineApprovalQueryExecutor, final JdbcQueryExecutor orderServiceQueryExecutor,
      final JdbcQueryExecutor paymentServiceQueryExecutor) {
    this.airlineMdmQueryExecutor = airlineMdmQueryExecutor;
    this.customerMdmQueryExecutor = customerMdmQueryExecutor;
    this.airlineApprovalQueryExecutor = airlineApprovalQueryExecutor;
    this.orderServiceQueryExecutor = orderServiceQueryExecutor;
    this.paymentServiceQueryExecutor = paymentServiceQueryExecutor;
  }

  public void inCustomerMdmWaitForCustomer(final Long id) {
    customerMdmQueryExecutor.waitForEntityWithId(CUSTOMERS_TABLE, id);
  }

  public void inCustomerMdmWaitForCustomersCount(final int count) {
    customerMdmQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public int inCustomerMdmGetCustomerCount() {
    return customerMdmQueryExecutor.getCount(CUSTOMERS_TABLE);
  }

  public void inOrderServiceWaitForCustomer(final Long id) {
    orderServiceQueryExecutor.waitForEntityWithId(CUSTOMERS_TABLE, id);
  }

  public void inOrderServiceWaitForCustomersCount(final int count) {
    orderServiceQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public void inPaymentServiceWaitForCustomer(final Long id) {
    paymentServiceQueryExecutor.waitForEntityWithId(CUSTOMERS_TABLE, id);
  }

  public void inPaymentServiceWaitForCustomersCount(final int count) {
    paymentServiceQueryExecutor.waitForCount(CUSTOMERS_TABLE, count);
  }

  public void inAirlineMdmWaitForAirline(final Long id) {
    airlineMdmQueryExecutor.waitForEntityWithId(AIRLINES_TABLE, id);
  }

  public void inAirlineApprovalWaitForAirline(final Long id) {
    airlineApprovalQueryExecutor.waitForEntityWithId(AIRLINES_TABLE, id);
  }

  public int inOrderServiceGetOrderCount() {
    return orderServiceQueryExecutor.getCount(ORDERS_TABLE);
  }

  public void inOrderServiceWaitForAirline(final Long id) {
    orderServiceQueryExecutor.waitForEntityWithId(AIRLINES_TABLE, id);
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
