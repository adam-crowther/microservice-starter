package com.acroteq.food.ordering.system.customer.service.domain.ports.output.repository;

import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  Customer save(Customer customer);

  Optional<Customer> findById(UUID customerId);

  void deleteById(UUID customerId);
}
