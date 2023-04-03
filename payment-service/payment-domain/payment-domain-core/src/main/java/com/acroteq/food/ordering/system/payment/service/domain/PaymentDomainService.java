package com.acroteq.food.ordering.system.payment.service.domain;

import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

  PaymentEvent validateAndInitiatePayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories);

  PaymentEvent validateAndCancelPayment(Payment payment,
                                        CreditEntry creditEntry,
                                        List<CreditHistory> creditHistories);
}
