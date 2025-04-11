package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class CustomerCommandProcessor {

  private final CustomerDomainService customerDomainService;
  private final CustomerRepository customerRepository;

  Customer createCustomer(final Customer customer) {
    log.info("Received create customer command");
    customerDomainService.validate(customer);
    return customerRepository.save(customer);
  }

  Customer updateCustomer(final Customer customer) {
    log.info("Received update customer command for id {}", customer.getId());
    customerDomainService.validate(customer);
    return customerRepository.save(customer);
  }

  void deleteCustomer(final CustomerId id) {
    log.info("Received delete customer command for id {}", id);
    customerRepository.deleteById(id);
  }
}
