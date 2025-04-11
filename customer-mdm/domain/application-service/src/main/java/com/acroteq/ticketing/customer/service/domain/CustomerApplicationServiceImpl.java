package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerEventMessagePublisher;
import com.acroteq.ticketing.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

  private final CustomerCommandProcessor commandProcessor;
  private final CustomerRepository repository;
  private final CustomerEventMessagePublisher eventPublisher;

  @Override
  @Transactional(readOnly = true)
  public List<Customer> loadAllCustomers() {
    return repository.loadAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Customer> loadCustomer(final CustomerId id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Customer createCustomer(final Customer customer) {
    final Customer savedCustomer = commandProcessor.createCustomer(customer);
    final CustomerEvent event = CustomerEvent.builder()
                                             .customer(savedCustomer)
                                             .build();
    eventPublisher.publish(event);
    return savedCustomer;
  }

  @Override
  @Transactional
  public void updateCustomer(final Customer customer) {
    final Customer savedCustomer = commandProcessor.updateCustomer(customer);
    final CustomerEvent event = CustomerEvent.builder()
                                             .customer(savedCustomer)
                                             .build();
    eventPublisher.publish(event);
  }

  @Override
  @Transactional
  public void deleteCustomer(final CustomerId customerId) {
    commandProcessor.deleteCustomer(customerId);
    eventPublisher.publishDelete(customerId);
  }
}
