package com.acroteq.food.ordering.system.payment.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.acroteq.food.ordering.system.payment.service.domain.exception.CreditEntryNotFoundException;
import com.acroteq.food.ordering.system.payment.service.domain.exception.CreditEntrySaveFailedException;
import com.acroteq.food.ordering.system.payment.service.domain.exception.CreditHistoryNotFoundException;
import com.acroteq.food.ordering.system.payment.service.domain.exception.CreditHistorySaveFailedException;
import com.acroteq.food.ordering.system.payment.service.domain.exception.PaymentNotFoundException;
import com.acroteq.food.ordering.system.payment.service.domain.exception.PaymentSaveFailedException;
import com.acroteq.food.ordering.system.payment.service.domain.mapper.PaymentDtoToDomainMapper;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.acroteq.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentProcessor {

  private final PaymentDomainService paymentDomainService;
  private final PaymentDtoToDomainMapper paymentDtoToDomainMapper;
  private final PaymentRepository paymentRepository;
  private final CreditEntryRepository creditEntryRepository;
  private final CreditHistoryRepository creditHistoryRepository;

  @Transactional
  public PaymentEvent processPayment(final PaymentRequestDto paymentRequestDto) {
    log.info("Received payment complete event for order id: {}", paymentRequestDto.getOrderId());
    final Payment payment = paymentDtoToDomainMapper.convertDtoToDomain(paymentRequestDto);
    final CustomerId customerId = payment.getCustomerId();
    final CreditEntry creditEntry = getCreditEntry(customerId);
    final List<CreditHistory> creditHistories = getCreditHistory(customerId);
    final PaymentEvent paymentEvent =
        paymentDomainService.validateAndInitiatePayment(payment,
                                                        creditEntry,
                                                        creditHistories);
    final ValidationResult result = paymentEvent.getResult();
    persistDbObjects(payment, creditEntry, creditHistories, result);
    return paymentEvent;
  }

  @Transactional
  public PaymentEvent cancelPayment(final PaymentRequestDto paymentRequestDto) {
    log.info("Received payment rollback event for order id: {}", paymentRequestDto.getOrderId());
    final UUID orderId = UUID.fromString(paymentRequestDto.getOrderId());
    final Optional<Payment> paymentResponse = paymentRepository.findByOrderId(orderId);
    if (paymentResponse.isEmpty()) {
      log.error("Payment with order id: {} could not be found!", orderId);
      throw new PaymentNotFoundException(orderId);
    }
    final Payment payment = paymentResponse.get();
    final CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
    final List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
    final PaymentEvent paymentEvent = paymentDomainService
        .validateAndCancelPayment(payment, creditEntry, creditHistories);
    final ValidationResult result = paymentEvent.getResult();
    persistDbObjects(payment, creditEntry, creditHistories, result);
    return paymentEvent;
  }

  private CreditEntry getCreditEntry(final CustomerId customerId) {
    final Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
    if (creditEntry.isEmpty()) {
      log.error("Could not find credit entry for customer: {}", customerId);
      throw new CreditEntryNotFoundException(customerId);
    }
    return creditEntry.get();
  }

  private List<CreditHistory> getCreditHistory(final CustomerId customerId) {
    final Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByCustomerId(customerId);
    if (creditHistories.isEmpty()) {
      log.error("Could not find credit history for customer: {}", customerId);
      throw new CreditHistoryNotFoundException(customerId);
    }
    return creditHistories.get();
  }

  private void persistDbObjects(final Payment payment,
                                final CreditEntry creditEntry,
                                final List<CreditHistory> creditHistories,
                                final ValidationResult result) {
    if (paymentRepository.save(payment) == null) {
      throw new PaymentSaveFailedException(payment.getId());
    }

    if (result.isPass()) {
      if (creditEntryRepository.save(creditEntry) == null) {
        throw new CreditEntrySaveFailedException(creditEntry.getId());
      }

      final CreditHistory creditHistory = creditHistories.get(creditHistories.size() - 1);
      final CreditHistory savedCreditHistory = creditHistoryRepository.save(creditHistory);
      if (savedCreditHistory == null) {
        throw new CreditHistorySaveFailedException(creditHistory.getId());
      }
    }
  }
}
