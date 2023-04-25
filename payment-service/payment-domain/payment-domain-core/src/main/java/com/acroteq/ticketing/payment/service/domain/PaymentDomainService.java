package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;

import java.util.List;

public interface PaymentDomainService {

  PaymentOutput validatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories);

  PaymentOutput cancelPayment(Payment payment, CreditEntry creditEntry);
}

