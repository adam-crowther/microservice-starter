package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.payment.service.domain.exception.CreditEntryNotFoundException.creditEntryNotFoundException;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditHistoryNotFoundException.creditHistoryNotFoundException;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerEventDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerEventProcessingOrderException;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.payment.service.domain.mapper.CustomerEventDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.CustomerEventMessageListener;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CustomerRepository customerRepository;
  private final CreditEntryRepository creditEntryRepository;
  private final CreditHistoryRepository creditHistoryRepository;
  private final CustomerEventDtoToDomainMapper customerEventMapper;
  private final CreditEntryDomainService creditEntryDomainService;

  @Transactional
  @Override
  public void customerCreatedOrUpdated(final CustomerEventDto dto) {
    final CustomerId id = CustomerId.of(dto.getId());
    log.info("Creating CreditEntry and adding CreditHistory for customer: {}", id);

    final Customer customer = customerEventMapper.convertDtoToDomain(dto);
    final Optional<CreditEntryOutput> output;

    final boolean alreadyExists = alreadyExists(customer);
    customerRepository.save(customer);

    if (!alreadyExists) {
      output = customerCreated(dto, customer);
    } else if (!alreadyProcessed(customer)) {
      output = customerUpdated(dto, id, customer);
    } else {
      output = Optional.empty();
    }

    output.map(CreditEntryOutput::getCreditEntry)
          .ifPresent(creditEntryRepository::save);
    output.map(CreditEntryOutput::getCreditHistory)
          .ifPresent(creditHistoryRepository::save);
  }

  private boolean alreadyExists(final Customer customer) {
    final CustomerId customerId = customer.getId();
    return customerRepository.existsById(customerId);
  }

  private boolean alreadyProcessed(final Customer newCustomer) {
    final CustomerId customerId = newCustomer.getId();
    final Customer existingEntity = customerRepository.findById(customerId)
                                                      .orElseThrow(() -> new CustomerNotFoundException(customerId));

    if (newCustomer.isFromAnEarlierEventThan(existingEntity)) {
      throw new CustomerEventProcessingOrderException(newCustomer.getId());
    }

    final boolean isAlreadyProcessed = newCustomer.isFromTheSameEventAs(existingEntity);
    if (isAlreadyProcessed) {
      log.debug("CustomerUpdatedEvent for Customer {} with eventId {} was already processed.",
                newCustomer.getId(),
                newCustomer.getEventId());
    }

    return isAlreadyProcessed;
  }

  private Optional<CreditEntryOutput> customerCreated(final CustomerEventDto dto, final Customer customer) {
    final Optional<CreditEntryOutput> output;
    final CreditEntry newCreditEntry = customerEventMapper.convertDtoToCreditEntry(dto);
    output = Optional.of(newCreditEntry)
                     .map(creditEntryDomainService::createCreditEntry);
    return output;
  }

  private Optional<CreditEntryOutput> customerUpdated(final CustomerEventDto dto,
                                                      final CustomerId id,
                                                      final Customer customer) {
    final Optional<CreditEntryOutput> output;
    customerRepository.save(customer);
    final CreditEntry updatedCreditEntry = customerEventMapper.convertDtoToCreditEntry(dto);
    final CreditEntry currentCreditEntry = creditEntryRepository.findByCustomerId(id)
                                                                .orElseThrow(creditEntryNotFoundException(id));
    final List<CreditHistory> historyList = creditHistoryRepository.findByCustomerId(id)
                                                                   .orElseThrow(creditHistoryNotFoundException(id));
    output = Optional.of(updatedCreditEntry)
                     .map(updateCreditEntry(currentCreditEntry, historyList));
    return output;
  }

  private Function<CreditEntry, CreditEntryOutput> updateCreditEntry(final CreditEntry currentCredit,
                                                                     final List<CreditHistory> historyList) {
    return updatedCredit -> creditEntryDomainService.updateCreditEntry(currentCredit, updatedCredit, historyList);
  }

  @Transactional
  @Override
  public void customerDeleted(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    log.info("Customer {} deleted. Setting credit limit and credit to zero.", customerId);
    customerRepository.findById(customerId)
                      .map(Customer::zeroCreditLimit)
                      .ifPresent(customerRepository::save);
    creditEntryRepository.findByCustomerId(customerId)
                         .map(CreditEntry::zeroCredit)
                         .ifPresent(creditEntryRepository::save);
  }
}
