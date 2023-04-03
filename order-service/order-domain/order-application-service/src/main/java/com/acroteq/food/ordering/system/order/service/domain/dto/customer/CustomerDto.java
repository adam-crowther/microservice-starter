package com.acroteq.food.ordering.system.order.service.domain.dto.customer;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CustomerDto {

  private UUID id;
  private String userName;
  private String firstName;
  private String lastName;
}
