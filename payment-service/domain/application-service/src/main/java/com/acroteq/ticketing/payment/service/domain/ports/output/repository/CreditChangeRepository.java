package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;

import java.util.List;
import java.util.Optional;

public interface CreditChangeRepository
    extends ReadRepository<CreditChangeId, CreditChange>, WriteRepository<CreditChangeId, CreditChange> {

  Optional<List<CreditChange>> findByCustomerId(CustomerId customerId);
}
