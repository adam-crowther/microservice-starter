package com.acroteq.ticketing.payment.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;

public interface PaymentRequestMessageListener {

  void completePayment(PaymentRequestDto paymentRequestDto);

  void cancelPayment(PaymentRequestDto paymentRequestDto);
}
