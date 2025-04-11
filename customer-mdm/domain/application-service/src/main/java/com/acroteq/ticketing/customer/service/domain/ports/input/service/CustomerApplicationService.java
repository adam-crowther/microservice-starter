package com.acroteq.ticketing.customer.service.domain.ports.input.service;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface CustomerApplicationService {

  List<Customer> loadAllCustomers();

  Optional<Customer> loadCustomer(CustomerId customerId);

  Customer createCustomer(@Valid Customer customer);

  void updateCustomer(@Valid Customer customer);

  void deleteCustomer(CustomerId customerId);
}
