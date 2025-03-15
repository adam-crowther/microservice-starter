package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static lombok.AccessLevel.PACKAGE;

import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.payment.service.domain.CreditBalanceDomainService;
import com.acroteq.ticketing.payment.service.domain.CreditBalanceDomainServiceImpl;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceOutput;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class CreditBalanceServiceStepDefinitions {

  private final CustomerEntityStepDefinitions customerEntitySteps;
  private final CreditBalanceEntityStepDefinitions creditBalanceEntitySteps;
  private final CreditBalanceOutputStepDefinitions creditBalanceOutputSteps;

  @Getter(PACKAGE)
  private CreditBalanceDomainService service;

  @Before(order = 1)
  public final void initService() {
    service = new CreditBalanceDomainServiceImpl();
  }

  @When("a new credit balance is created")
  public void aNewCreditBalanceIsCreated() {
    final Customer customer = customerEntitySteps.getCustomer();
    final CreditBalanceOutput output = service.createNewCreditBalance(customer);
    creditBalanceOutputSteps.setCreditBalance(output.getCreditBalance());
    creditBalanceOutputSteps.setCreditChange(output.getCreditChange());
  }

  @When("the credit limit is updated to {currency} {amount}")
  public void theCreditLimitIsUpdatedTo(final CurrencyId currencyId, final BigDecimal amount) {
    final Customer customer = customerEntitySteps.getCustomer();
    final CashValue oldCreditLimit = customer.getCreditLimit();
    final CashValue newCreditLimit = CashValue.builder()
                                              .currencyId(currencyId)
                                              .amount(amount)
                                              .build();
    final CreditBalance oldBalance = creditBalanceEntitySteps.getCreditBalance();
    final List<CreditChange> creditHistory = creditBalanceEntitySteps.getCreditHistory();
    final CreditBalanceOutput output = service.updateCreditLimit(newCreditLimit,
                                                                 oldCreditLimit,
                                                                 oldBalance,
                                                                 creditHistory);
    creditBalanceOutputSteps.setCreditBalance(output.getCreditBalance());
    creditBalanceOutputSteps.setCreditChange(output.getCreditChange());
  }
}
