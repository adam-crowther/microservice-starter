package com.acroteq.ticketing.customer.service.domain.entity;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.ticketing.customer.service.domain.exception.CustomerValidationException;
import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {

  private final String userName;
  private final String firstName;
  private final String lastName;

  public void validate() {
    checkPrecondition(isNotBlank(userName), CustomerValidationException::new, "userName");
    checkPrecondition(isNotBlank(firstName), CustomerValidationException::new, "firstName");
    checkPrecondition(isNotBlank(lastName), CustomerValidationException::new, "lastName");
  }
}
