package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.event.CustomerEvent;
import com.acroteq.ticketing.customer.service.domain.mapper.CreateCustomerDtoToDomainMapper;
import com.acroteq.ticketing.customer.service.domain.mapper.UpdateCustomerDtoToDomainMapper;
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
  private final CreateCustomerDtoToDomainMapper createCustomerMapper;
  private final UpdateCustomerDtoToDomainMapper updateCustomerMapper;

  CustomerEvent createCustomer(final CreateCustomerCommandDto createCustomerCommandDto) {
    log.info("Received create customer command");
    final Customer customer = createCustomerMapper.convertDtoToDomain(createCustomerCommandDto);
    customerDomainService.validate(customer);
    final Customer savedCustomer = customerRepository.save(customer);

    return CustomerEvent.builder()
                        .customer(savedCustomer)
                        .build();
  }

  CustomerEvent updateCustomer(final UpdateCustomerCommandDto updateCustomerCommandDto) {
    log.info("Received update customer command for id {}", updateCustomerCommandDto.getId());
    final Customer customer = updateCustomerMapper.convertDtoToDomain(updateCustomerCommandDto);
    customerDomainService.validate(customer);
    final Customer savedCustomer = customerRepository.save(customer);

    return CustomerEvent.builder()
                        .customer(savedCustomer)
                        .build();
  }

  void deleteCustomer(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    log.info("Received delete customer command for id {}", customerId);
    customerRepository.deleteById(customerId);
  }
}
