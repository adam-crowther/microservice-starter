package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CreditBalanceEntityStepDefinitions {

  private static final long CREDIT_BALANCE_ID = 432L;
  private static final long CREDIT_CHANGE_ID = 555L;

  public final CustomerEntityStepDefinitions customerEntitySteps;
  @Getter(PACKAGE)
  private final List<CreditChange> creditHistory = new ArrayList<>();
  @Getter(PACKAGE)
  @Setter(PACKAGE)
  private CreditBalance creditBalance;

  @Before(order = 5010)
  public final void initialiseCreditBalance() {
    final Customer customer = customerEntitySteps.getCustomer();
    creditBalance = CreditBalance.builder()
                                 .id(CreditBalanceId.of(CREDIT_BALANCE_ID))
                                 .customer(customer)
                                 .totalCredit(CashValue.ZERO)
                                 .build();
  }

  @Given("a credit balance of {currency} {amount}")
  public final void aCreditBalanceOf(final CurrencyId currencyId, final BigDecimal amount) {
    final CashValue totalCredit = CashValue.builder()
                                           .currencyId(currencyId)
                                           .amount(amount)
                                           .build();
    final Customer customer = customerEntitySteps.getCustomer();
    creditBalance = creditBalance.toBuilder()
                                 .customer(customer)
                                 .totalCredit(totalCredit)
                                 .build();
  }

  @Given("a {eventType} credit change of {currency} {amount}")
  public final void aCustomerOf(final CreditChangeType eventType,
                                final CurrencyId currencyId,
                                final BigDecimal amount) {
    final Customer customer = customerEntitySteps.getCustomer();
    final CashValue creditDelta = CashValue.builder()
                                           .currencyId(currencyId)
                                           .amount(amount.abs())
                                           .build();
    final TransactionType transactionType = amount.compareTo(BigDecimal.ZERO) >= 0 ? CREDIT : DEBIT;
    final Long id = CREDIT_CHANGE_ID + creditHistory.size();
    final CreditChange creditChange = CreditChange.builder()
                                                  .id(CreditChangeId.of(id))
                                                  .customer(customer)
                                                  .transactionType(transactionType)
                                                  .creditChangeType(eventType)
                                                  .creditDelta(creditDelta)
                                                  .build();
    creditHistory.add(creditChange);
  }

  @ParameterType("(payment|credit limit update)")
  public final CreditChangeType eventType(final String input) {
    return CreditChangeType.of(input)
                           .orElseThrow(() -> new IllegalArgumentException("Unknown CreditChangeType: " + input));
  }

}
