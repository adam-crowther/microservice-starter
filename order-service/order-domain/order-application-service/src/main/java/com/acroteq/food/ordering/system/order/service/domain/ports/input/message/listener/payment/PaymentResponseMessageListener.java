package com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponseDto;

public interface PaymentResponseMessageListener {

  void paymentCompleted(PaymentResponseDto paymentResponse);

  void paymentCancelled(PaymentResponseDto paymentResponse);
}
