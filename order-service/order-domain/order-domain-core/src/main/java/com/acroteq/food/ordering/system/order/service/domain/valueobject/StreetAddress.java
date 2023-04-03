package com.acroteq.food.ordering.system.order.service.domain.valueobject;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
