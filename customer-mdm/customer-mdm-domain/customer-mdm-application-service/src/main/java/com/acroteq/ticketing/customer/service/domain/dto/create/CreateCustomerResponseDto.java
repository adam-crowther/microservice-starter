package com.acroteq.ticketing.customer.service.domain.dto.create;

import com.acroteq.ticketing.application.dto.AuditedEntityDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateCustomerResponseDto extends AuditedEntityDto {

  @NotNull
  private final String message;
}
