package com.acroteq.food.ordering.system.customer.service.domain;

import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import com.acroteq.food.ordering.system.customer.service.domain.exception.CustomerSaveFailedException;
import com.acroteq.food.ordering.system.customer.service.domain.mapper.CreateCustomerDtoToDomainMapper;
import com.acroteq.food.ordering.system.customer.service.domain.mapper.UpdateCustomerDtoToDomainMapper;
import com.acroteq.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerCommandProcessor {

  private final CustomerDomainService customerDomainService;
  private final CustomerRepository customerRepository;
  private final CreateCustomerDtoToDomainMapper createCustomerMapper;
  private final UpdateCustomerDtoToDomainMapper updateCustomerMapper;

  @Transactional
  public CustomerEvent createCustomer(final CreateCustomerCommandDto createCustomerCommandDto) {
    log.info("Received create customer command");
    final Customer customer = createCustomerMapper.convertDtoToDomain(createCustomerCommandDto);
    final CustomerEvent customerEvent =
        customerDomainService.validateAndCreateCustomer(customer);
    final Customer savedCustomer = customerRepository.save(customer);
    if (savedCustomer == null) {
      throw new CustomerSaveFailedException(customerEvent.getCustomerId());
    }

    return customerEvent;
  }

  @Transactional
  public CustomerEvent updateCustomer(final UpdateCustomerCommandDto updateCustomerCommandDto) {
    log.info("Received update customer command for id {}", updateCustomerCommandDto.getId());
    final Customer customer = updateCustomerMapper.convertDtoToDomain(updateCustomerCommandDto);
    final CustomerEvent customerEvent =
        customerDomainService.validateAndUpdateCustomer(customer);
    final Customer savedCustomer = customerRepository.save(customer);
    if (savedCustomer == null) {
      throw new CustomerSaveFailedException(customerEvent.getCustomerId());
    }
    return customerEvent;
  }

  @Transactional
  public CustomerEvent deleteCustomer(final UUID customerId) {
    log.info("Received delete customer command for id {}", CustomerId.of(customerId));
    final CustomerEvent customerEvent = customerDomainService.deleteCustomer(CustomerId.of(customerId));
    customerRepository.deleteById(customerId);

    return customerEvent;
  }
}
