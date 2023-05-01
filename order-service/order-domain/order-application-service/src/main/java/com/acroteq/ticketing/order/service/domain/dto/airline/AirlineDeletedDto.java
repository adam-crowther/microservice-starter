package com.acroteq.ticketing.order.service.domain.dto.airline;

import com.acroteq.ticketing.application.dto.Dto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AirlineDeletedDto implements Dto {

  private Long id;
}
