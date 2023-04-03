package com.acroteq.food.ordering.system.payment.service.domain.ports.input.message.listener;

import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;

public interface PaymentRequestMessageListener {

  void completePayment(PaymentRequestDto paymentRequestDto);

  void cancelPayment(PaymentRequestDto paymentRequestDto);
}
