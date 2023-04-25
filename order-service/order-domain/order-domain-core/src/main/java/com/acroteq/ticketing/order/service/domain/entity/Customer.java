package com.acroteq.ticketing.order.service.domain.entity;

import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Customer extends AggregateRoot<CustomerId> {

  @NonNull
  private final String userName;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
}
