package com.acroteq.ticketing.customer.service.domain.ports.output.repository;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.domain.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

  Customer save(Customer customer);

  Optional<Customer> findById(CustomerId customerId);

  void deleteById(CustomerId customerId);
}
