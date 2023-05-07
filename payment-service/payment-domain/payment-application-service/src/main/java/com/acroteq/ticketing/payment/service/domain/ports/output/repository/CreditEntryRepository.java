package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;

import java.util.Optional;

public interface CreditEntryRepository
    extends ReadRepository<CreditEntryId, CreditEntry>, WriteRepository<CreditEntryId, CreditEntry> {

  Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
