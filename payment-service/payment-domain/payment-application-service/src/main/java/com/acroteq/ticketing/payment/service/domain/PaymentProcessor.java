package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.event.PaymentEvent;
import com.acroteq.ticketing.payment.service.domain.event.PaymentFailedEvent;
import com.acroteq.ticketing.payment.service.domain.exception.CreditEntryNotFoundException;
import com.acroteq.ticketing.payment.service.domain.exception.CreditHistoryNotFoundException;
import com.acroteq.ticketing.payment.service.domain.exception.PaymentNotFoundException;
import com.acroteq.ticketing.payment.service.domain.mapper.PaymentDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.PaymentRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
class PaymentProcessor {

  private final PaymentDomainService paymentDomainService;
  private final PaymentDtoToDomainMapper paymentDtoToDomainMapper;
  private final OrderIdMapper idMapper;
  private final PaymentRepository paymentRepository;
  private final CreditEntryRepository creditEntryRepository;
  private final CreditHistoryRepository creditHistoryRepository;

  PaymentEvent processPayment(final PaymentRequestDto paymentRequestDto) {
    log.info("Received payment complete event for order id: {}", paymentRequestDto.getOrderId());
    final Payment payment = paymentDtoToDomainMapper.convertDtoToDomain(paymentRequestDto);
    final CustomerId customerId = payment.getCustomerId();
    final CreditEntry creditEntry = getCreditEntry(customerId);
    final List<CreditHistory> creditHistories = getCreditHistory(customerId);

    final PaymentOutput paymentOutput = paymentDomainService.validatePayment(payment, creditEntry, creditHistories);
    final Payment savedPayment = persistDbObjects(paymentOutput);

    final ValidationResult result = paymentOutput.getValidationResult();
    final UUID sagaId = paymentRequestDto.getSagaId();
    return PaymentFailedEvent.builder()
                             .sagaId(sagaId)
                             .payment(savedPayment)
                             .result(result)
                             .build();
  }

  PaymentEvent cancelPayment(final PaymentRequestDto paymentRequestDto) {
    final OrderId orderId = idMapper.convertLongToId(paymentRequestDto.getOrderId());

    log.info("Received payment rollback event for order id: {}", orderId);
    final Optional<Payment> paymentResponse = paymentRepository.findByOrderId(orderId);
    if (paymentResponse.isEmpty()) {
      log.error("Payment with order id: {} could not be found!", orderId);
      throw new PaymentNotFoundException(orderId);
    }
    final Payment payment = paymentResponse.get();
    final CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());

    final PaymentOutput paymentOutput = paymentDomainService.cancelPayment(payment, creditEntry);
    final Payment savedPayment = persistDbObjects(paymentOutput);

    final ValidationResult result = paymentOutput.getValidationResult();
    final UUID sagaId = paymentRequestDto.getSagaId();
    return PaymentFailedEvent.builder()
                             .sagaId(sagaId)
                             .payment(savedPayment)
                             .result(result)
                             .build();
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

  private Payment persistDbObjects(final PaymentOutput paymentOutput) {
    final ValidationResult result = paymentOutput.getValidationResult();
    if (result.isPass()) {
      final CreditEntry creditEntry = paymentOutput.getCreditEntry();
      creditEntryRepository.save(creditEntry);

      final CreditHistory creditHistory = paymentOutput.getCreditHistory();
      creditHistoryRepository.save(creditHistory);
    }

    final Payment payment = paymentOutput.getPayment();
    return paymentRepository.save(payment);
  }
}
