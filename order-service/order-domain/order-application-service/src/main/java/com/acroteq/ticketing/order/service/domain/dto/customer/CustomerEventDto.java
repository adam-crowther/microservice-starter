package com.acroteq.ticketing.order.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class CustomerEventDto extends ReplicatedEntityDto {

  @NotNull
  private final String userName;
  @NotNull
  private final String firstName;
  @NotNull
  private final String lastName;
}
