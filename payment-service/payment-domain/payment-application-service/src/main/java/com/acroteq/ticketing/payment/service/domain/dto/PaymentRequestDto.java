package com.acroteq.ticketing.payment.service.domain.dto;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PaymentRequestDto {

  private Long id;
  private String sagaId;
  private Long orderId;
  private Long customerId;
  private CashValue value;
  private Instant createdDateTime;
  private PaymentStatus paymentStatus;

  public void setPaymentOrderStatus(final PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }
}
