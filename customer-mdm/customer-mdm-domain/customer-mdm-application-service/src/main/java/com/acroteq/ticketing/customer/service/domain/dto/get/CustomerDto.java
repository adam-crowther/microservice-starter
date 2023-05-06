package com.acroteq.ticketing.customer.service.domain.dto.get;

import com.acroteq.ticketing.application.dto.AuditedEntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CustomerDto extends AuditedEntityDto {

  @NotNull
  private String userName;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private CashValue creditLimit;
}
