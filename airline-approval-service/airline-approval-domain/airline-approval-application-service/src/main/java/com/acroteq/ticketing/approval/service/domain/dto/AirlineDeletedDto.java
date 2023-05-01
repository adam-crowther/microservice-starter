package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.ticketing.application.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AirlineDeletedDto implements Dto {

  @NotNull
  private Long id;
}

