package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.domain.valueobject.Audit;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PaymentEntityStepDefinitions {

  private static final OrderId ORDER_ID = OrderId.of(5678L);

  public final CustomerEntityStepDefinitions customerEntitySteps;

  @Getter(PACKAGE)
  private Payment payment;

  @Before(order = 5050)
  public final void initialisePayment() {
    final Customer customer = customerEntitySteps.getCustomer();
    payment = Payment.builder()
                     .status(PaymentStatus.PENDING)
                     .customer(customer)
                     .orderId(ORDER_ID)
                     .orderVersion(0L)
                     .audit(Audit.now())
                     .value(CashValue.ZERO)
                     .build();
  }

  @Given("a {paymentStatus} payment value of {currency} {amount}")
  public final void aPaymentOf(final PaymentStatus paymentStatus,
                               final CurrencyId currencyId,
                               final BigDecimal amount) {
    final CashValue value = CashValue.builder()
                                     .currencyId(currencyId)
                                     .amount(amount)
                                     .build();
    payment = payment.toBuilder()
                     .status(paymentStatus)
                     .value(value)
                     .build();
  }

  @ParameterType("(pending|completed|cancelled|failed)")
  public final PaymentStatus paymentStatus(final String input) {
    return PaymentStatus.of(input)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentStatus: " + input));
  }

  @ParameterType("(approved|rejected)")
  public final OrderApprovalStatus approvalStatus(final String input) {
    return OrderApprovalStatus.of(input)
                              .orElseThrow(() -> new IllegalArgumentException("Unknown OrderApprovalStatus: " + input));
  }

  @ParameterType("([A-Z]{3})")
  public final CurrencyId currency(final String input) {
    return CurrencyId.of(input);
  }

  @ParameterType("([\\-\\+]?[0-9]+(\\.[0-9]+)?)")
  public final BigDecimal amount(final String input) {
    return new BigDecimal(input);
  }
}
