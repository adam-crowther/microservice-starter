package com.acroteq.ticketing.customer.service.domain.dto.create;

import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateCustomerCommandDto implements DataTransferObject {

  @NotNull
  private final String userName;
  @NotNull
  private final String firstName;
  @NotNull
  private final String lastName;
  @NotNull
  private final CashValue creditLimit;
}
