package com.acroteq.ticketing.payment.service.domain.dto.payment;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class PaymentRequestDto implements Dto {

  private final UUID sagaId;
  private final Long orderId;
  private final Long customerId;
  private final CashValue value;
  private final Instant createdDateTime;
}
