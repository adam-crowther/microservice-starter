package com.acroteq.ticketing.customer.service.domain.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCustomerCommandDto {

  @NotNull
  private String id;
  @NotNull
  private String userName;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
}
