package com.acroteq.ticketing.customer.service.client.matcher;

import com.acroteq.ticketing.customer.service.client.model.CreateCustomer;
import com.acroteq.ticketing.customer.service.client.model.Customer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

@RequiredArgsConstructor
public class CustomerMatcher extends TypeSafeMatcher<Customer> {

  private static final double DOUBLE_COMPARISON_EPSILON = 10E-6;
  private final CreateCustomer request;

  public static CustomerMatcher matches(final CreateCustomer request) {
    return new CustomerMatcher(request);
  }

  @Override
  protected boolean matchesSafely(final Customer customer) {

    return Objects.equals(customer.getUserName(), request.getUserName()) && Objects.equals(customer.getFirstName(),
                                                                                           request.getFirstName())
           && Objects.equals(customer.getLastName(), request.getLastName())
           && Precision.equals(customer.getCreditLimitAmount(),
                               request.getCreditLimitAmount(),
                               DOUBLE_COMPARISON_EPSILON) && Objects.equals(customer.getCreditLimitCurrencyId(),
                                                                            request.getCreditLimitCurrencyId());


  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
  }
}
