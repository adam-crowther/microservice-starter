package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository
    extends ReadRepository<CreditHistoryId, CreditHistory>, WriteRepository<CreditHistoryId, CreditHistory> {

  Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
