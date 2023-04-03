package com.acroteq.food.ordering.system.customer.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerCommandDto {

  @NotNull
  private String userName;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
}
