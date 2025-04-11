package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditBalanceNotFoundException.creditBalanceNotFoundException;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditChangeNotFoundException.creditChangeNotFoundException;
import static com.acroteq.ticketing.payment.service.domain.exception.CustomerNotFoundException.customerNotFoundException;
import static java.lang.Long.parseLong;

import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerEventProcessingOrderException;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.CustomerEventMessageListener;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditBalanceRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditChangeRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceOutput;
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
  private final CreditBalanceRepository creditBalanceRepository;
  private final CreditChangeRepository creditChangeRepository;
  private final CreditBalanceDomainService creditBalanceDomainService;

  @Transactional
  @Override
  public void customerCreatedOrUpdated(final Customer customer) {
    final CustomerId id = customer.getId();
    log.info("Creating CreditBalance and adding CreditHistory for customer: {}", id);
    final Optional<CreditBalanceOutput> output;

    if (doesNotAlreadyExist(customer)) {
      output = customerCreated(customer);
    } else if (isNotAlreadyProcessed(customer)) {
      output = customerUpdated(customer, id);
    } else {
      output = Optional.empty();
    }

    output.map(CreditBalanceOutput::getCreditBalance)
          .ifPresent(creditBalanceRepository::save);
    output.map(CreditBalanceOutput::getCreditChange)
          .ifPresent(creditChangeRepository::save);
  }

  private boolean doesNotAlreadyExist(final Customer customer) {
    final CustomerId customerId = customer.getId();
    return !customerRepository.existsById(customerId);
  }

  private boolean isNotAlreadyProcessed(final Customer newCustomer) {
    final CustomerId customerId = newCustomer.getId();
    final Customer existingEntity = customerRepository.findById(customerId)
                                                      .orElseThrow(() -> new CustomerNotFoundException(customerId));

    if (newCustomer.isFromAnEarlierEventThan(existingEntity)) {
      throw new CustomerEventProcessingOrderException(newCustomer.getId());
    }

    final boolean alreadyProcessed = newCustomer.isFromTheSameEventAs(existingEntity);
    if (alreadyProcessed) {
      log.debug("CustomerUpdatedEvent for Customer {} with eventId {} was already processed.",
                newCustomer.getId(),
                newCustomer.getEventId());
    }

    return !alreadyProcessed;
  }

  private Optional<CreditBalanceOutput> customerCreated(final Customer customer) {
    customerRepository.save(customer);
    return Optional.of(customer)
                   .map(creditBalanceDomainService::createNewCreditBalance);
  }

  private Optional<CreditBalanceOutput> customerUpdated(final Customer customer, final CustomerId id) {
    customerRepository.save(customer);

    final CashValue newCreditLimit = customer.getCreditLimit();
    final CashValue oldCreditLimit = customerRepository.findById(id)
                                                       .map(Customer::getCreditLimit)
                                                       .orElseThrow(customerNotFoundException(id));

    final CreditBalance oldCreditBalance = creditBalanceRepository.findByCustomerId(id)
                                                                  .orElseThrow(creditBalanceNotFoundException(id));
    final List<CreditChange> creditHistory = creditChangeRepository.findByCustomerId(id)
                                                                   .orElseThrow(creditChangeNotFoundException(id));
    return Optional.of(newCreditLimit)
                   .map(updateCreditLimit(oldCreditLimit, oldCreditBalance, creditHistory));
  }

  private Function<CashValue, CreditBalanceOutput> updateCreditLimit(
      final CashValue oldCreditLimit,
      final CreditBalance oldCreditBalance,
      final List<CreditChange> creditHistory) {
    return newCreditLimit -> creditBalanceDomainService.updateCreditLimit(newCreditLimit,
                                                                          oldCreditLimit,
                                                                          oldCreditBalance,
                                                                          creditHistory);
  }

  @Transactional
  @Override
  public void customerDeleted(final String key) {
    log.info("Customer {} deleted. Setting credit limit and credit to zero.", key);
    final CustomerId customerId = CustomerId.of(parseLong(key));
    customerRepository.findById(customerId)
                      .ifPresent(this::setCreditLimitToZero);
  }

  private void setCreditLimitToZero(final Customer oldCustomer) {
    final CashValue oldCreditLimit = oldCustomer.getCreditLimit();
    final CustomerId id = oldCustomer.getId();
    final CreditBalance oldCreditBalance = creditBalanceRepository.findByCustomerId(id)
                                                                  .orElseThrow(creditBalanceNotFoundException(id));
    final List<CreditChange> creditHistory = creditChangeRepository.findByCustomerId(id)
                                                                   .orElseThrow(creditChangeNotFoundException(id));

    final Customer customer = oldCustomer.withZeroCreditLimit();
    customerRepository.save(customer);

    final CreditBalanceOutput output = creditBalanceDomainService.updateCreditLimit(ZERO,
                                                                                    oldCreditLimit,
                                                                                    oldCreditBalance,
                                                                                    creditHistory);
    creditBalanceRepository.save(output.getCreditBalance());
    Optional.of(output)
            .map(CreditBalanceOutput::getCreditChange)
            .ifPresent(creditChangeRepository::save);
  }
}
