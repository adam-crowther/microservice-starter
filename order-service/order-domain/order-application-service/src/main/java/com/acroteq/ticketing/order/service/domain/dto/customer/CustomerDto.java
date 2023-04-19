package com.acroteq.ticketing.order.service.domain.dto.customer;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerDto {

  private Long id;
  private String userName;
  private String firstName;
  private String lastName;
}
