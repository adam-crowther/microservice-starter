package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.ticketing.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.ticketing.customer.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.customer.service.domain.mapper.CustomerCreatedEventToResponseDtoMapper;
import com.acroteq.ticketing.customer.service.domain.mapper.CustomerDomainToDtoMapper;
import com.acroteq.ticketing.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerCreatedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerDeletedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.domain.ports.output.message.publisher.CustomerUpdatedEventMessagePublisher;
import com.acroteq.ticketing.customer.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

  private final CustomerCommandProcessor commandProcessor;
  private final CustomerRepository repository;
  private final CustomerCreatedEventMessagePublisher createdEventPublisher;
  private final CustomerUpdatedEventMessagePublisher updatedEventPublisher;
  private final CustomerDeletedEventMessagePublisher deletedEventPublisher;
  private final CustomerDomainToDtoMapper domainDtoMapper;
  private final CustomerCreatedEventToResponseDtoMapper eventDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public CustomerDto getCustomer(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    final Customer customer = repository.findById(customerId)
                                        .orElseThrow(() -> new CustomerNotFoundException(customerId));
    return domainDtoMapper.convertDomainToDto(customer);
  }

  @Override
  @Transactional
  public CreateCustomerResponseDto createCustomer(final CreateCustomerCommandDto createCustomerCommandDto) {
    final CustomerCreatedEvent customerEvent = commandProcessor.createCustomer(createCustomerCommandDto);
    createdEventPublisher.publish(customerEvent);
    return eventDtoMapper.convertEventToDto(customerEvent);
  }

  @Override
  @Transactional
  public void updateCustomer(final UpdateCustomerCommandDto updateCustomerCommandDto) {
    final CustomerUpdatedEvent customerEvent = commandProcessor.updateCustomer(updateCustomerCommandDto);
    updatedEventPublisher.publish(customerEvent);
  }

  @Override
  @Transactional
  public void deleteCustomer(final Long customerId) {
    final CustomerDeletedEvent customerEvent = commandProcessor.deleteCustomer(customerId);
    deletedEventPublisher.publish(customerEvent);
  }
}
