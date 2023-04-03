package com.acroteq.food.ordering.system.customer.service.domain.event;

import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.event.visitor.CustomerEventVisitor;
import com.acroteq.food.ordering.system.domain.event.DomainEvent;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class CustomerEvent extends DomainEvent<Customer> {

  public abstract CustomerId getCustomerId();

  public abstract <T> T accept(final CustomerEventVisitor<T> visitor);

  public abstract String getEventType();
}
