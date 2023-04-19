package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {

  Optional<Customer> findCustomer(CustomerId customerId);

  boolean customerExists(CustomerId customerId);

  Customer saveCustomer(Customer customer);

  void deleteCustomer(CustomerId customerId);
}
