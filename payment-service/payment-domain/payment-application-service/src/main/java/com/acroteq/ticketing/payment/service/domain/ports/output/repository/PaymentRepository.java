package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;

import java.util.Optional;

public interface PaymentRepository {

  Payment save(Payment payment);

  Optional<Payment> findByOrderId(OrderId orderId);
}
