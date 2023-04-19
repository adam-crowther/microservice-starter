package com.acroteq.ticketing.customer.service.domain.dto.get;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDto {

  private String id;
  private String userName;
  private String firstName;
  private String lastName;
}
