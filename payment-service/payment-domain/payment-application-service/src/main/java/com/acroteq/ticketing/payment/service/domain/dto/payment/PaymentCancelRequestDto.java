package com.acroteq.ticketing.payment.service.domain.dto.payment;

import com.acroteq.ticketing.application.dto.Dto;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PaymentCancelRequestDto implements Dto {

  private final UUID sagaId;
  private final Long orderId;
  private final Long paymentId;
}
