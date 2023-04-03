package com.acroteq.food.ordering.system.payment.service.domain.dto;

import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PaymentRequestDto {

  private String id;
  private String sagaId;
  private String orderId;
  private String customerId;
  private CashValue value;
  private Instant createdDateTime;
  private PaymentOrderStatus paymentOrderStatus;

  public void setPaymentOrderStatus(final PaymentOrderStatus paymentOrderStatus) {
    this.paymentOrderStatus = paymentOrderStatus;
  }
}
