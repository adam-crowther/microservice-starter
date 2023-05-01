package com.acroteq.ticketing.payment.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerDeletedDto implements Dto {

  @NotNull
  private Long id;
}
