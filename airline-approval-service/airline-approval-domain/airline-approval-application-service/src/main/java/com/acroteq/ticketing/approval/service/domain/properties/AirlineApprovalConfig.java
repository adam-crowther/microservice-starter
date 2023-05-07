package com.acroteq.ticketing.approval.service.domain.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AirlineApprovalConfig {

  @NotNull String requestTopicName;
  @NotNull String responseTopicName;
}
