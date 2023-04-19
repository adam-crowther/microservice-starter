package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.mapper.CustomerDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CustomerRepository customerRepository;
  private final CustomerDtoToDomainMapper customerDtoToDomainMapper;

  @Transactional
  @Override
  public void customerCreated(final CustomerDto customerDto) {
    log.info("Creating Customer: {}", customerDto.getId());
    final Customer customer = customerDtoToDomainMapper.convertDtoToDomain(customerDto);
    customerRepository.saveCustomer(customer);
  }

  @Transactional
  @Override
  public void customerUpdated(final CustomerDto customerDto) {
    log.info("Updating Customer: {}", customerDto.getId());
    final Customer customer = customerDtoToDomainMapper.convertDtoToDomain(customerDto);
    customerRepository.saveCustomer(customer);
  }

  @Transactional
  @Override
  public void customerDeleted(final Long id) {
    log.info("Deleting Customer: {}", id);
    final CustomerId customerId = CustomerId.of(id);
    customerRepository.deleteCustomer(customerId);
  }
}
