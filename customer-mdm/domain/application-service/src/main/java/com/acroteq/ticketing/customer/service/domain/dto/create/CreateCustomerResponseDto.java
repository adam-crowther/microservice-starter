package com.acroteq.ticketing.customer.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.AuditedEntityDto;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CreateCustomerResponseDto extends AuditedEntityDto {

  @NotNull
  private final String message;
}
