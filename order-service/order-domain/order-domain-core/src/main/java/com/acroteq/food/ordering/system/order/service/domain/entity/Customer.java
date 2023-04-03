package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {

  private String userName;
  private String firstName;
  private String lastName;
}
