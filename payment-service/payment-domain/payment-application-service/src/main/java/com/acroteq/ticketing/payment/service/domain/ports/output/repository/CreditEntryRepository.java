package com.acroteq.ticketing.payment.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;

public interface CreditEntryRepository
    extends ReadRepository<CustomerId, CreditEntry>, WriteRepository<CustomerId, CreditEntry> {}
