package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.entity.Customer;

public interface CustomerRepository
    extends ReadRepository<CustomerId, Customer>, WriteRepository<CustomerId, Customer> {}
