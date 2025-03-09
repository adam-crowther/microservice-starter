package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId;

import java.util.Optional;

public interface CreditBalanceRepository
    extends ReadRepository<CreditBalanceId, CreditBalance>, WriteRepository<CreditBalanceId, CreditBalance> {

  Optional<CreditBalance> findByCustomerId(CustomerId customerId);
}
