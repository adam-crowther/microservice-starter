package com.acroteq.food.ordering.system.order.service.domain.valueobject;

import lombok.*;

import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode(exclude = "id")
@ToString
public class StreetAddress {

  UUID id;
  @NonNull String street;
  @NonNull String postalCode;
  @NonNull String city;
}
