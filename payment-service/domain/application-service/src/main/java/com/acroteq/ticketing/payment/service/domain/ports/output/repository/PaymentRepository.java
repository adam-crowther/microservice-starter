package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.domain.valueobject.PaymentId;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends ReadRepository<PaymentId, Payment>, WriteRepository<PaymentId, Payment> {

  Optional<Payment> findByOrderId(OrderId orderId);
}
