package com.acroteq.ticketing.customer.service.domain.dto.get;

import com.acroteq.ticketing.application.dto.AuditedEntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CustomerDto extends AuditedEntityDto {

  @NonNull
  private String userName;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private CashValue creditLimit;
}
