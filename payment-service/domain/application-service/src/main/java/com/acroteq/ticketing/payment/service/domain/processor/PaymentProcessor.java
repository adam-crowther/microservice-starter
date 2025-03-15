package com.acroteq.ticketing.payment.service.domain.processor;

import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.payment.service.domain.PaymentDomainService;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentCancelRequestDto;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.event.PaymentEvent;
import com.acroteq.ticketing.payment.service.domain.exception.CreditBalanceNotFoundException;
import com.acroteq.ticketing.payment.service.domain.exception.CreditChangeNotFoundException;
import com.acroteq.ticketing.payment.service.domain.exception.MissingCreditChangeException;
import com.acroteq.ticketing.payment.service.domain.exception.PaymentNotFoundException;
import com.acroteq.ticketing.payment.service.domain.mapper.PaymentDtoToDomainMapper;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditBalanceRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CreditChangeRepository;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.PaymentRepository;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentProcessor {

  private final PaymentDomainService paymentDomainService;
  private final PaymentDtoToDomainMapper paymentDtoToDomainMapper;
  private final OrderIdMapper idMapper;
  private final PaymentRepository paymentRepository;
  private final CreditBalanceRepository creditBalanceRepository;
  private final CreditChangeRepository creditChangeRepository;

  public PaymentEvent processPayment(final PaymentRequestDto dto) {
    log.info("Received payment requested event for order id: {}", dto.getOrderId());
    final Payment payment = paymentDtoToDomainMapper.convertDtoToDomain(dto);
    final CustomerId customerId = payment.getCustomer()
                                         .getId();
    final CreditBalance creditBalance = getCreditBalance(customerId);
    final List<CreditChange> creditHistory = getCreditHistory(customerId);

    final PaymentOutput paymentOutput = paymentDomainService.validatePayment(payment, creditBalance, creditHistory);
    final Payment savedPayment = persistDbObjects(paymentOutput);

    final ValidationResult result = paymentOutput.getValidationResult();
    final UUID sagaId = dto.getSagaId();

    return PaymentEvent.polymorphicBuilder()
                       .sagaId(sagaId)
                       .payment(savedPayment)
                       .result(result)
                       .build();
  }

  public PaymentEvent cancelPayment(final PaymentCancelRequestDto dto) {
    final OrderId orderId = idMapper.convertLongToId(dto.getOrderId());

    log.info("Received payment rollback event for order id: {}", orderId);
    final Payment payment = paymentRepository.findByOrderId(orderId)
                                             .orElseThrow(() -> new PaymentNotFoundException(orderId));
    final CreditBalance creditBalance = getCreditBalance(payment.getCustomer()
                                                                .getId());

    final PaymentOutput paymentOutput = paymentDomainService.cancelPayment(payment, creditBalance);
    final Payment savedPayment = persistDbObjects(paymentOutput);

    final ValidationResult result = paymentOutput.getValidationResult();
    final UUID sagaId = dto.getSagaId();
    return PaymentEvent.polymorphicBuilder()
                       .sagaId(sagaId)
                       .payment(savedPayment)
                       .result(result)
                       .build();
  }

  private CreditBalance getCreditBalance(final CustomerId customerId) {
    return creditBalanceRepository.findByCustomerId(customerId)
                                  .orElseThrow(() -> new CreditBalanceNotFoundException(customerId));
  }

  private List<CreditChange> getCreditHistory(final CustomerId customerId) {
    return creditChangeRepository.findByCustomerId(customerId)
                                 .orElseThrow(() -> new CreditChangeNotFoundException(customerId));
  }

  private Payment persistDbObjects(final PaymentOutput paymentOutput) {
    final ValidationResult result = paymentOutput.getValidationResult();
    final Payment payment = paymentOutput.getPayment();
    if (result.isPass()) {
      final CreditBalance creditBalance = paymentOutput.getCreditBalance();
      creditBalanceRepository.save(creditBalance);

      final CreditChange creditChange = paymentOutput.getCreditChange()
                                                     .orElseThrow(() -> new MissingCreditChangeException(payment.getId()));
      creditChangeRepository.save(creditChange);
    }

    return paymentRepository.save(payment);
  }
}
