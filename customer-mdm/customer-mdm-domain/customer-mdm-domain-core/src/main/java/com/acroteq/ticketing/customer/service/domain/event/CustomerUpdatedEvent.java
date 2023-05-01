package com.acroteq.ticketing.customer.service.domain.event;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class CustomerUpdatedEvent extends CustomerEvent {

  private final Customer customer;

  @Override
  public CustomerId getCustomerId() {
    return customer.getId();
  }
}
