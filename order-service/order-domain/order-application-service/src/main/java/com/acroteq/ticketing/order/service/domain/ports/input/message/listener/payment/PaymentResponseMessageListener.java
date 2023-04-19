package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.payment;

import com.acroteq.ticketing.order.service.domain.dto.message.PaymentResponseDto;

public interface PaymentResponseMessageListener {

  void paymentCompleted(PaymentResponseDto paymentResponse);

  void paymentCancelled(PaymentResponseDto paymentResponse);
}
