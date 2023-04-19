package com.acroteq.ticketing.customer.service.domain.event;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.event.visitor.CustomerEventVisitor;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class CustomerUpdateFailedEvent extends CustomerEvent {

  private static final String EVENT_TYPE = CustomerUpdateFailedEvent.class.getSimpleName();

  private final Customer customer;

  @Override
  public CustomerId getCustomerId() {
    return customer.getId();
  }

  @Override
  public <T> T accept(final CustomerEventVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}
