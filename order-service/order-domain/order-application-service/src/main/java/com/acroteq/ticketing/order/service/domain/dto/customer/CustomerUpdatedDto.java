package com.acroteq.ticketing.order.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.Dto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerUpdatedDto implements Dto {

  private Long id;
  private String userName;
  private String firstName;
  private String lastName;
}
