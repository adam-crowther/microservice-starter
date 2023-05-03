package com.acroteq.ticketing.customer.service.domain.ports.output.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

public interface CustomerRepository
    extends ReadRepository<CustomerId, Customer>, WriteRepository<CustomerId, Customer> {}
