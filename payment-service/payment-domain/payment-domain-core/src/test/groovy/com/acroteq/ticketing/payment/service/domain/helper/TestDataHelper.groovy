package com.acroteq.ticketing.payment.service.domain.helper


import com.acroteq.ticketing.domain.valueobject.*
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange
import com.acroteq.ticketing.payment.service.domain.entity.Customer
import com.acroteq.ticketing.payment.service.domain.entity.Payment
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId

import java.time.Instant

import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.PENDING
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.CREDIT_LIMIT_UPDATE
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT

class TestDataHelper {

  static final CustomerId CUSTOMER_ID = CustomerId.of(777)
  static final CreditBalanceId CREDIT_BALANCE_ID = CreditBalanceId.of(888)
  static final Integer CREDIT_BALANCE_VERSION = 2
  static final CreditChangeId CREDIT_CHANGE_ID = CreditChangeId.of(999)
  static final Integer CREDIT_CHANGE_VERSION = 3
  static final PaymentId PAYMENT_ID = PaymentId.of(987)
  static final Integer PAYMENT_VERSION = 2
  static final OrderId ORDER_ID = OrderId.of(654)
  static final Integer ORDER_VERSION = 3

  static final String CURRENCY = 'CHF'
  static final BigDecimal AMOUNT = 2000

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochMilli(1684327300754)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochMilli(1684327300754)

  static final Integer PARTITION = 3
  static final Long OFFSET = 34524

  static CashValue createCashValue() {
    return createCashValue(AMOUNT)
  }

  static CashValue createCashValue(BigDecimal amount) {
    def currencyId = CurrencyId.of(CURRENCY)
    return CashValue.builder()
          .currencyId(currencyId)
          .amount(amount)
          .build()
  }


  static EventId createEventId() {
    return EventId.builder()
          .partition(PARTITION)
          .offset(OFFSET)
          .build()
  }

  static Customer createCustomer() {
    def creditLimit = createCashValue()
    def eventId = createEventId()
    return Customer.builder()
          .id(CUSTOMER_ID)
          .version(765)
          .eventId(eventId)
          .creditLimit(creditLimit)
          .build()
  }

  static Audit createAudit() {
    return Audit.builder()
          .createdTimestamp(CREATED_TIMESTAMP)
          .lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP)
          .build()
  }

  static CreditBalance createCreditBalance() {
    def totalCredit = createCashValue()
    def customer = createCustomer()

    return CreditBalance.builder()
          .id(CREDIT_BALANCE_ID)
          .version(CREDIT_BALANCE_VERSION)
          .totalCredit(totalCredit)
          .customer(customer)
          .build()
  }

  static CreditChange createCreditChange() {
    def creditDelta = createCashValue()
    def customer = createCustomer()

    return CreditChange.builder()
          .id(CREDIT_CHANGE_ID)
          .version(CREDIT_CHANGE_VERSION)
          .customer(customer)
          .creditDelta(creditDelta)
          .transactionType(CREDIT)
          .creditChangeType(CREDIT_LIMIT_UPDATE)
          .build()
  }

  static createPayment() {
    def customer = createCustomer()
    def audit = createAudit()
    def value = createCashValue()

    return Payment.builder()
          .id(PAYMENT_ID)
          .version(PAYMENT_VERSION)
          .audit(audit)
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(customer)
          .value(value)
          .status(PENDING)
          .build()
  }

}
