package com.acroteq.food.ordering.system.payment.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.PaymentStatus;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.acroteq.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.acroteq.food.ordering.system.payment.service.domain.entity.Payment;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.acroteq.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import com.acroteq.food.ordering.system.payment.service.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.fail;
import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {

  @Override
  public PaymentEvent validateAndInitiatePayment(final Payment payment,
                                                 final CreditEntry creditEntry,
                                                 final List<CreditHistory> creditHistories) {
    final ValidationResult result = payment.validatePayment();
    payment.initializePayment();
    result.add(validateCreditEntry(payment, creditEntry));
    subtractCreditEntry(payment, creditEntry);
    updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
    result.add(validateCreditHistory(creditEntry, creditHistories));

    if (result.isPass()) {
      log.info("Payment is initiated for order id: {}", payment.getOrderId());
      payment.updateStatus(PaymentStatus.COMPLETED);
      return PaymentCompletedEvent.builder()
                                  .payment(payment)
                                  .build();
    } else {
      log.info("Payment initiation is failed for order id: {}", payment.getOrderId());
      payment.updateStatus(PaymentStatus.FAILED);
      return PaymentFailedEvent.builder()
                               .payment(payment)
                               .result(result)
                               .build();
    }
  }

  @Override
  public PaymentEvent validateAndCancelPayment(final Payment payment,
                                               final CreditEntry creditEntry,
                                               final List<CreditHistory> creditHistories) {
    final ValidationResult result = payment.validatePayment();
    addCreditEntry(payment, creditEntry);
    updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);

    if (result.isPass()) {
      log.info("Payment is cancelled for order id: {}", payment.getOrderId());
      payment.updateStatus(PaymentStatus.CANCELLED);
      return PaymentCancelledEvent.builder()
                                  .payment(payment)
                                  .build();
    } else {
      log.info("Payment cancellation is failed for order id: {}", payment.getOrderId());
      payment.updateStatus(PaymentStatus.FAILED);
      return PaymentFailedEvent.builder()
                               .payment(payment)
                               .result(result)
                               .build();
    }
  }

  private ValidationResult validateCreditEntry(final Payment payment,
                                               final CreditEntry creditEntry) {
    if (payment.getValue()
               .isGreaterThan(creditEntry.getTotalCredit())) {
      log.error("Customer with id: {} doesn't have enough credit for payment!", payment.getCustomerId());
      return fail("Customer with id %s  doesn't have enough credit for payment!", payment.getCustomerId());
    }

    return pass();
  }

  private void subtractCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    creditEntry.subtractCredit(payment.getValue());
  }

  private void updateCreditHistory(final Payment payment,
                                   final List<CreditHistory> creditHistories,
                                   final TransactionType transactionType) {
    final CreditHistory creditHistory = CreditHistory.builder()
                                                     .id(CreditHistoryId.random())
                                                     .customerId(payment.getCustomerId())
                                                     .credit(payment.getValue())
                                                     .transactionType(transactionType)
                                                     .build();
    creditHistories.add(creditHistory);
  }


  private ValidationResult validateCreditHistory(final CreditEntry creditEntry,
                                                 final List<CreditHistory> creditHistories) {
    final CashValue totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
    final CashValue totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

    final ValidationResult validationResult = pass();

    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
      log.error("Customer with id: {} doesn't have enough credit according to credit history",
                creditEntry.getCustomerId());


      validationResult.addFailureMessage("Customer with id %s doesn't have enough credit according to credit history",
                                         creditEntry.getCustomerId());
    }

    if (!creditEntry.getTotalCredit()
                    .equals(totalCreditHistory.subtract(totalDebitHistory))) {
      log.error("Credit history total is not equal to current credit for customer id: {}!",
                creditEntry.getCustomerId());
      validationResult.addFailureMessage("Credit history total is not equal to current credit for customer id: %s",
                                         creditEntry.getCustomerId());
    }

    return validationResult;
  }

  private CashValue getTotalHistoryAmount(final List<CreditHistory> creditHistories,
                                          final TransactionType transactionType) {
    return creditHistories.stream()
                          .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                          .map(CreditHistory::getCredit)
                          .reduce(CashValue.ZERO, CashValue::add);
  }

  private void addCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    creditEntry.addCredit(payment.getValue());
  }
}
