package com.acroteq.ticketing.customer.service.domain.event;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.event.visitor.CustomerEventVisitor;
import com.acroteq.ticketing.domain.event.DomainEvent;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class CustomerEvent extends DomainEvent<Customer> {

  public abstract CustomerId getCustomerId();

  public abstract <T> T accept(CustomerEventVisitor<T> visitor);

  public abstract String getEventType();
}
