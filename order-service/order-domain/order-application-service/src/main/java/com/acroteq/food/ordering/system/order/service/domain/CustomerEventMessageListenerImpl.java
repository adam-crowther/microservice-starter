package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.domain.dto.customer.CustomerDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.order.service.domain.mapper.CustomerDtoToDomainMapper;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CustomerRepository customerRepository;
  private final CustomerDtoToDomainMapper customerDtoToDomainMapper;

  @Override
  public void customerCreated(final CustomerDto customerDto) {
    log.info("Creating Customer: {}", customerDto.getId());
    final Customer customer = customerDtoToDomainMapper.convertDtoToDomain(customerDto);
    customerRepository.saveCustomer(customer);
  }

  @Override
  public void customerUpdated(final CustomerDto customerDto) {
    log.info("Updating Customer: {}", customerDto.getId());
    final Customer customer = customerDtoToDomainMapper.convertDtoToDomain(customerDto);
    customerRepository.saveCustomer(customer);
  }

  @Override
  public void customerDeleted(final UUID id) {
    log.info("Deleting Customer: {}", id);
    final CustomerId customerId = CustomerId.of(id);
    customerRepository.deleteCustomer(customerId);
  }
}
