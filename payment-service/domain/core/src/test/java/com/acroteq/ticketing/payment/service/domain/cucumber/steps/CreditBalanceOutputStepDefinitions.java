package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;
import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PACKAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import io.cucumber.java.en.Then;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreditBalanceOutputStepDefinitions {

  @Getter(PACKAGE)
  @Setter(PACKAGE)
  private CreditBalance creditBalance;

  @Getter(PACKAGE)
  @Setter(PACKAGE)
  private CreditChange creditChange;

  @Then("the updated credit balance is {currency} {amount}")
  public final void theNewCreditBalanceIs(final CurrencyId currencyId, final BigDecimal amount) {
    final CreditBalance creditBalance = getCreditBalance();
    final CashValue expectedBalance = CashValue.builder()
                                               .currencyId(currencyId)
                                               .amount(amount)
                                               .build();
    assertThat(creditBalance.getTotalCredit(), is(equalTo(expectedBalance)));
  }

  @Then("the new credit change is {eventType} of {currency} {amount}")
  public final void theNewCustomerIs(final CreditChangeType creditType,
                                     final CurrencyId currencyId,
                                     final BigDecimal amount) {
    final CashValue creditDelta = CashValue.builder()
                                           .currencyId(currencyId)
                                           .amount(amount.abs())
                                           .build();
    final TransactionType transactionType = amount.compareTo(ZERO) <= 0 ? DEBIT : CREDIT;

    assertThat(creditChange.getCreditChangeType(), is(creditType));
    assertThat(creditChange.getCreditDelta(), is(creditDelta));
    assertThat(creditChange.getTransactionType(), is(transactionType));
  }

  @Then("there is no new credit change")
  public final void thereIsNoNewCustomer() {
    assertThat(creditChange, is(nullValue()));
  }
}
