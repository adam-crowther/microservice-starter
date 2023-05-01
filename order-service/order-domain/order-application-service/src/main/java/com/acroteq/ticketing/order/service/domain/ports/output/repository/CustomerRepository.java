package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {

  Optional<Customer> findById(CustomerId customerId);

  boolean existsById(CustomerId customerId);

  Customer insert(Customer customer);

  Customer update(Customer customer);

  void deleteById(CustomerId customerId);
}
