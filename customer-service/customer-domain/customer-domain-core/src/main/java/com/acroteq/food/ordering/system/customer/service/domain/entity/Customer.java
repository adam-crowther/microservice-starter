package com.acroteq.food.ordering.system.customer.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {

  private final String userName;
  private final String firstName;
  private final String lastName;

  public void initializeCustomer() {
    setId(CustomerId.random());
  }

  public ValidationResult validateCustomer() {
    final ValidationResult result = pass();

    if (isBlank(userName)) {
      result.addFailureMessage("User name must be set to non-blank value!");
    }
    if (isBlank(firstName)) {
      result.addFailureMessage("First name must be set to non-blank value!");
    }
    if (isBlank(lastName)) {
      result.addFailureMessage("Last name must be set to non-blank value!");
    }

    return result;
  }
}
