package com.acroteq.ticketing.order.service.domain;

import static java.lang.Long.parseLong;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.exception.CustomerEventProcessingOrderException;
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

  @Transactional
  @Override
  public void customerCreatedOrUpdated(final Customer customer) {
    log.info("Creating Customer: {}", customer.getId());
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
  public void customerDeleted(final String key) {
    final CustomerId customerId = CustomerId.of(parseLong(key));
    log.info("Deleting Customer: {}", customerId);
    customerRepository.deleteById(customerId);
  }
}
