package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.payment;

import com.acroteq.ticketing.order.service.domain.dto.message.PaymentCancelledResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentPaidResponseDto;

public interface PaymentResponseMessageListener {

  void paymentCompleted(PaymentPaidResponseDto paymentResponse);

  void paymentCancelled(PaymentCancelledResponseDto paymentResponse);
}
