package com.acroteq.ticketing.order.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.ReplicatedEntityDto;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class CustomerEventDto extends ReplicatedEntityDto {

  @NonNull
  private final String userName;
  @NonNull
  private final String firstName;
  @NonNull
  private final String lastName;
}
