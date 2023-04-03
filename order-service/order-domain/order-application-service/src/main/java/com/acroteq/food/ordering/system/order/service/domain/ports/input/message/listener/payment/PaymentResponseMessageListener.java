package com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import org.mapstruct.Mapper;

public interface PaymentResponseMessageListener {

  void paymentCompleted(PaymentResponse paymentResponse);

  void paymentCancelled(PaymentResponse paymentResponse);
}
