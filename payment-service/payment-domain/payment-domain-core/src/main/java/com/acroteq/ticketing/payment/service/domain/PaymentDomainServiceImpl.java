package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.validation.ValidationResult.fail;
import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;
import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.COMPLETED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.FAILED;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.validation.ValidationResult.ValidationResultBuilder;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {

  @Override
  public PaymentOutput validatePayment(final Payment payment,
                                       final CreditEntry creditEntry,
                                       final List<CreditHistory> creditHistories) {
    final ValidationResultBuilder resultBuilder = ValidationResult.builder();
    resultBuilder.addValidationResult(payment.validatePayment());

    resultBuilder.addValidationResult(validateCreditEntry(payment, creditEntry));
    subtractCreditEntry(payment, creditEntry);
    updateCreditHistory(payment, creditHistories, DEBIT);
    resultBuilder.addValidationResult(validateCreditHistory(creditEntry, creditHistories));

    final ValidationResult result = resultBuilder.build();
    final PaymentStatus updatedStatus = result.isPass() ? COMPLETED : FAILED;
    final Payment updatedPayment = payment.toBuilder()
                                          .paymentStatus(updatedStatus)
                                          .build();
    log.info("Payment is {} for order id: {}", updatedStatus, payment.getOrderId());
    return new PaymentOutput(updatedPayment, result);
  }

  @Override
  public PaymentOutput cancelPayment(final Payment payment,
                                     final CreditEntry creditEntry,
                                     final List<CreditHistory> creditHistories) {
    final ValidationResult result = payment.validatePayment();
    addCreditEntry(payment, creditEntry);
    updateCreditHistory(payment, creditHistories, CREDIT);
    final PaymentStatus updatedStatus = result.isPass() ? CANCELLED : FAILED;
    final Payment updatedPayment = payment.toBuilder()
                                          .paymentStatus(updatedStatus)
                                          .build();
    log.info("Payment cancellation is {} for order id: {}", updatedStatus, payment.getOrderId());
    return new PaymentOutput(updatedPayment, result);
  }

  private ValidationResult validateCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    final ValidationResult result;
    if (payment.getValue()
               .isGreaterThan(creditEntry.getTotalCredit())) {
      log.error("Customer with id: {} doesn't have enough credit for payment!", payment.getCustomerId());
      result = fail("Customer with id %s  doesn't have enough credit for payment!", payment.getCustomerId());
    } else {
      result = pass();
    }

    return result;
  }

  private void subtractCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    creditEntry.subtractCredit(payment.getValue());
  }

  private void updateCreditHistory(final Payment payment,
                                   final List<CreditHistory> creditHistories,
                                   final TransactionType transactionType) {
    final CreditHistory creditHistory = CreditHistory.builder()
                                                     .customerId(payment.getCustomerId())
                                                     .credit(payment.getValue())
                                                     .transactionType(transactionType)
                                                     .build();
    creditHistories.add(creditHistory);
  }


  private ValidationResult validateCreditHistory(final CreditEntry creditEntry,
                                                 final List<CreditHistory> creditHistories) {
    final CashValue totalCreditHistory = getTotalHistoryAmount(creditHistories, CREDIT);
    final CashValue totalDebitHistory = getTotalHistoryAmount(creditHistories, DEBIT);

    final ValidationResultBuilder validationResult = ValidationResult.builder();

    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
      log.error("Customer with id: {} doesn't have enough credit according to credit history",
                creditEntry.getCustomerId());


      validationResult.addFailure("Customer with id %s doesn't have enough credit according to credit history",
                                  creditEntry.getCustomerId());
    }

    if (!creditEntry.getTotalCredit()
                    .equals(totalCreditHistory.subtract(totalDebitHistory))) {
      log.error("Credit history total is not equal to current credit for customer id: {}!",
                creditEntry.getCustomerId());
      validationResult.addFailure("Credit history total is not equal to current credit for customer id: %s",
                                  creditEntry.getCustomerId());
    }

    return validationResult.build();
  }

  private CashValue getTotalHistoryAmount(final List<CreditHistory> creditHistories,
                                          final TransactionType transactionType) {
    return creditHistories.stream()
                          .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                          .map(CreditHistory::getCredit)
                          .reduce(ZERO, CashValue::add);
  }

  private void addCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    creditEntry.addCredit(payment.getValue());
  }
}
