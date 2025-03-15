package com.acroteq.ticketing.customer.service.domain.dto.get;

import com.acroteq.application.dto.AuditedEntityDto;
import com.acroteq.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CustomerDto extends AuditedEntityDto {

  @NotNull
  private final String userName;
  @NotNull
  private final String firstName;
  @NotNull
  private final String lastName;
  @NotNull
  private final CashValue creditLimit;
}
