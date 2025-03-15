package com.acroteq.ticketing.order.service.domain;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerEventDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.exception.CustomerEventProcessingOrderException;
import com.acroteq.ticketing.order.service.domain.mapper.customer.CustomerEventDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CustomerRepository customerRepository;
  private final CustomerEventDtoToDomainMapper createdMapper;

  @Transactional
  @Override
  public void customerCreatedOrUpdated(final CustomerEventDto dto) {
    log.info("Creating Customer: {}", dto.getId());
    final Customer customer = createdMapper.convertDtoToDomain(dto);
    if (!eventAlreadyProcessed(customer)) {
      customerRepository.save(customer);
    }
  }

  private boolean eventAlreadyProcessed(final Customer newCustomer) {
    final CustomerId customerId = newCustomer.getId();
    return customerRepository.findById(customerId)
                             .map(alreadyProcessed(newCustomer))
                             .orElse(false);
  }

  private Function<Customer, Boolean> alreadyProcessed(final Customer newCustomer) {
    return existingCustomer -> alreadyProcessed(existingCustomer, newCustomer);
  }

  private Boolean alreadyProcessed(final Customer existingCustomer, final Customer newCustomer) {

    if (newCustomer.isFromAnEarlierEventThan(existingCustomer)) {
      throw new CustomerEventProcessingOrderException(newCustomer.getId());
    }

    final boolean isAlreadyProcessed = newCustomer.isFromTheSameEventAs(existingCustomer);
    if (isAlreadyProcessed) {
      log.debug("CustomerUpdatedEvent for Customer {} with eventId {} was already processed.",
                newCustomer.getId(),
                newCustomer.getEventId());
    }

    return isAlreadyProcessed;
  }


  @Transactional
  @Override
  public void customerDeleted(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    log.info("Deleting Customer: {}", customerId);
    customerRepository.deleteById(customerId);
  }
}
