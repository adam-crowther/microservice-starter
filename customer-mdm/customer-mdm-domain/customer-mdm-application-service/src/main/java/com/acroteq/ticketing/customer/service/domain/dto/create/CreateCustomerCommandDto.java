package com.acroteq.ticketing.customer.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerCommandDto implements Dto {

  @NotNull
  private String userName;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private CashValue creditLimit;
}
