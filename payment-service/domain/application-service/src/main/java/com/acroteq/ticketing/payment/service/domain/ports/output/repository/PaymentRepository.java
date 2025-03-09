package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.PaymentId;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends ReadRepository<PaymentId, Payment>, WriteRepository<PaymentId, Payment> {

  Optional<Payment> findByOrderId(OrderId orderId);
}
