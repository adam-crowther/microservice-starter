package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditEntryNotFoundException.newCreditEntryNotFoundException;
import static com.acroteq.ticketing.payment.service.domain.exception.CreditHistoryNotFoundException.newCreditHistoryNotFoundException;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerCreatedDto;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDeletedDto;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerUpdatedDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.mapper.CustomerCreatedDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.mapper.CustomerDeletedDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.mapper.CustomerUpdatedDtoToDomainMapper;
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
  private final CustomerCreatedDtoToDomainMapper customerCreatedMapper;
  private final CustomerUpdatedDtoToDomainMapper customerUpdatedMapper;
  private final CustomerDeletedDtoToDomainMapper customerDeletedMapper;
  private final CreditEntryDomainService creditEntryDomainService;

  @Transactional
  @Override
  public void customerCreated(final CustomerCreatedDto dto) {
    final CustomerId customerId = CustomerId.of(dto.getId());
    log.info("Creating CreditEntry and adding CreditHistory for customer: {}", customerId);

    final CreditEntry creditEntry = customerCreatedMapper.convertDtoToDomain(dto);
    final CreditEntryOutput output = creditEntryDomainService.createCreditEntry(creditEntry);

    creditEntryRepository.save(output.getCreditEntry());
    creditHistoryRepository.save(output.getCreditHistory());
  }

  @Transactional
  @Override
  public void customerUpdated(final CustomerUpdatedDto dto) {
    final CustomerId customerId = CustomerId.of(dto.getId());
    log.info("Updating CreditEntry and adding CreditHistory for customer: {}", customerId);
    final CreditEntry updatedCreditEntry = customerUpdatedMapper.convertDtoToDomain(dto);
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
  public void customerDeleted(final CustomerDeletedDto dto) {
    final CustomerId customerId = customerDeletedMapper.convertDtoToDomain(dto);
    log.info("Customer {} deleted. Setting credit to zero", customerId);
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
