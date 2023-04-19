package com.acroteq.ticketing.order.service.domain.valueobject;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class StreetAddress {

  @NonNull String street;
  @NonNull String postalCode;
  @NonNull String city;
}
