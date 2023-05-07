package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderAddressDto implements DataTransferObject {

  @NotNull
  @Max(50)
  private final String street;

  @Max(10)
  @NotNull
  private final String postalCode;

  @Max(50)
  @NotNull
  private final String city;
}
