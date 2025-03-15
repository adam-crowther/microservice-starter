package com.acroteq.ticketing.customer.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;

public interface CustomerRepository
    extends ReadRepository<CustomerId, Customer>, WriteRepository<CustomerId, Customer> {}
