package com.acroteq.ticketing.order.service.domain.dto.customer;

import com.acroteq.ticketing.application.dto.Dto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerDeletedDto implements Dto {

  private Long id;
}
