package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;

public interface CustomerDomainService {

  void validate(Customer customer);
}
