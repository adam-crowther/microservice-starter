package com.acroteq.ticketing.payment.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CustomerEventDto extends ReplicatedEntityDto {

  @NotNull
  private CashValue creditLimit;
}
