package com.acroteq.food.ordering.system.order.service.domain.ports.output.repository;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {

  Optional<Customer> findCustomer(CustomerId customerId);

  boolean customerExists(CustomerId customerId);

  Customer saveCustomer(Customer customer);

  void deleteCustomer(CustomerId customerId);
}
