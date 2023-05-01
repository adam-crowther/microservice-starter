package com.acroteq.ticketing.customer.service.domain.dto.get;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CustomerDto implements Dto {

  @NonNull
  private String id;
  @NonNull
  private String userName;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private CashValue creditLimit;
}
