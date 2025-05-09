package com.acroteq.ticketing.payment.service.domain.cucumber.steps;

import static lombok.AccessLevel.PACKAGE;

import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CurrencyId;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CustomerEntityStepDefinitions {

  private static final CustomerId CUSTOMER_ID = CustomerId.of(1234L);
  private static final String USER_NAME = "UserName";
  private static final Integer PARTITION = 2;
  private static final Long OFFSET = 5678L;

  @Getter(PACKAGE)
  @Setter(PACKAGE)
  private Customer customer;

  @Before(order = 5000)
  public void initialiseCustomer() {
    final EventId eventId = EventId.builder()
                                   .partition(PARTITION)
                                   .offset(OFFSET)
                                   .build();
    customer = Customer.builder()
                       .id(CUSTOMER_ID)
                       .version(0L)
                       .eventId(eventId)
                       .userName(USER_NAME)
                       .creditLimit(CashValue.ZERO)
                       .build();
  }


  @Given("a customer with a credit limit of {currency} {amount}")
  public final void aCustomerWithCreditLimitOf(final CurrencyId currencyId, final BigDecimal amount) {
    final CashValue creditLimit = CashValue.builder()
                                           .currencyId(currencyId)
                                           .amount(amount)
                                           .build();
    customer = customer.toBuilder()
                       .creditLimit(creditLimit)
                       .build();
  }
}
