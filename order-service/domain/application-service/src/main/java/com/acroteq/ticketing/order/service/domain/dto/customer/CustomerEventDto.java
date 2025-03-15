package com.acroteq.ticketing.order.service.domain.dto.customer;

import com.acroteq.application.dto.ReplicatedEntityDto;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CustomerEventDto extends ReplicatedEntityDto {

  @NotNull
  private final String userName;
  @NotNull
  private final String firstName;
  @NotNull
  private final String lastName;
}
