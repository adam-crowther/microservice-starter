package com.acroteq.food.ordering.system.customer.service.domain;

import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import com.acroteq.food.ordering.system.customer.service.domain.exception.CustomerNotFoundException;
import com.acroteq.food.ordering.system.customer.service.domain.mapper.CustomerCreatedEventToResponseDtoMapper;
import com.acroteq.food.ordering.system.customer.service.domain.mapper.CustomerDomainToDtoMapper;
import com.acroteq.food.ordering.system.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.acroteq.food.ordering.system.customer.service.domain.ports.output.message.publisher.CustomerEventMessagePublisher;
import com.acroteq.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

  private final CustomerCommandProcessor commandProcessor;
  private final CustomerRepository customerRepository;
  private final CustomerEventMessagePublisher customerEventPublisher;
  private final CustomerDomainToDtoMapper customerDomainToDtoMapper;
  private final CustomerCreatedEventToResponseDtoMapper customerCreatedEventToResponseDtoMapper;

  @Override
  public CustomerDto getCustomer(final UUID customerId) {
    final Customer customer = customerRepository.findById(customerId)
                                                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    return customerDomainToDtoMapper.convertDomainToDto(customer);
  }

  @Override
  public CreateCustomerResponseDto createCustomer(final CreateCustomerCommandDto createCustomerCommandDto) {
    final CustomerEvent customerEvent = commandProcessor.createCustomer(createCustomerCommandDto);
    customerEventPublisher.publish(customerEvent);
    return customerCreatedEventToResponseDtoMapper.convertCreatedEventToResponseDto(customerEvent);
  }

  @Override
  public void updateCustomer(final UpdateCustomerCommandDto updateCustomerCommandDto) {
    final CustomerEvent customerEvent = commandProcessor.updateCustomer(updateCustomerCommandDto);
    customerEventPublisher.publish(customerEvent);
  }

  @Override
  public void deleteCustomer(final UUID customerId) {
    final CustomerEvent customerEvent = commandProcessor.deleteCustomer(customerId);
    customerEventPublisher.publish(customerEvent);
  }
}
