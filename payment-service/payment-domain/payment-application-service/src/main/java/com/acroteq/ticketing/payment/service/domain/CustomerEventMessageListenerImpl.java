package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditEntryNotFoundException.newCreditEntryNotFoundException;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditHistoryNotFoundException.newCreditHistoryNotFoundException;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.mapper.CustomerDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.CustomerEventMessageListener;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CreditEntryRepository creditEntryRepository;
  private final CreditHistoryRepository creditHistoryRepository;
  private final CustomerDtoToDomainMapper customerDtoToDomainMapper;
  private final CreditEntryDomainService creditEntryDomainService;

  @Transactional
  @Override
  public void customerCreated(final CustomerDto customerDto) {
    final CustomerId customerId = CustomerId.of(customerDto.getId());
    log.info("Creating CreditEntry and adding CreditHistory for customer: {}", customerId);

    final CreditEntry creditEntry = customerDtoToDomainMapper.convertDtoToCreditEntryDomain(customerDto);
    final CreditEntryOutput output = creditEntryDomainService.createCreditEntry(creditEntry);

    creditEntryRepository.save(output.getCreditEntry());
    creditHistoryRepository.save(output.getCreditHistory());
  }

  @Transactional
  @Override
  public void customerUpdated(final CustomerDto customerDto) {
    final CustomerId customerId = CustomerId.of(customerDto.getId());
    log.info("Updating CreditEntry and adding CreditHistory for customer: {}", customerId);
    final CreditEntry updatedCreditEntry = customerDtoToDomainMapper.convertDtoToCreditEntryDomain(customerDto);
    final CreditEntry currentCreditEntry = creditEntryRepository.findByCustomerId(customerId)
                                                                .orElseThrow(newCreditEntryNotFoundException(customerId));

    final List<CreditHistory> creditHistoryList = creditHistoryRepository.findByCustomerId(customerId)
                                                                         .orElseThrow(newCreditHistoryNotFoundException(
                                                                             customerId));

    final CreditEntryOutput output = creditEntryDomainService.updateCreditEntry(updatedCreditEntry,
                                                                                currentCreditEntry,
                                                                                creditHistoryList);

    creditEntryRepository.save(output.getCreditEntry());
    creditHistoryRepository.save(output.getCreditHistory());
  }

  @Transactional
  @Override
  public void customerDeleted(final Long id) {
    log.info("Customer {} deleted. Setting credit to zero", id);
    final CustomerId customerId = CustomerId.of(id);
    creditEntryRepository.findByCustomerId(customerId)
                         .map(this::withZeroCredit)
                         .ifPresent(creditEntryRepository::save);
  }

  private CreditEntry withZeroCredit(final CreditEntry creditEntry) {
    return creditEntry.toBuilder()
                      .totalCredit(ZERO)
                      .build();
  }
}
