package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;

import java.util.List;

public interface PaymentDomainService {

  PaymentOutput validatePayment(Payment payment, CreditBalance creditBalance, List<CreditChange> creditHistory);

  PaymentOutput cancelPayment(Payment payment, CreditBalance creditBalance);
}

