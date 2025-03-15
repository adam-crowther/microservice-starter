package com.acroteq.ticketing.customer.service.domain.event;

import com.acroteq.domain.event.EntityEvent;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

@ToString
@Builder
@Value
public class CustomerEvent implements EntityEvent {

  @NonNull Customer customer;

  @Override
  public Optional<Long> getId() {
    return Optional.of(customer)
                   .map(Customer::getId)
                   .map(EntityId::getValue);
  }
}
