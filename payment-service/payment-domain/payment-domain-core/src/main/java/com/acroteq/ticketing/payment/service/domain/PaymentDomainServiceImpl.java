package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.validation.ValidationResult.fail;
import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;
import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.COMPLETED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.FAILED;
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType.PAYMENT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.validation.ValidationResultBuilder;
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
    resultBuilder.validationResult(payment.validatePayment());

    resultBuilder.validationResult(validateCreditEntry(payment, creditEntry));
    final CreditEntry updatedCreditEntry = subtractCreditEntry(payment, creditEntry);
    final CreditHistory newCreditHistory = updateCreditHistory(payment, DEBIT);
    creditHistories.add(newCreditHistory);
    resultBuilder.validationResult(validateCreditHistory(updatedCreditEntry, creditHistories));

    final ValidationResult result = resultBuilder.build();
    final PaymentStatus updatedStatus = result.isPass() ? COMPLETED : FAILED;
    final Payment updatedPayment = payment.toBuilder()
                                          .paymentStatus(updatedStatus)
                                          .build();

    log.info("Payment is {} for order id: {}", updatedStatus, payment.getOrderId());
    return PaymentOutput.builder()
                        .payment(updatedPayment)
                        .creditEntry(updatedCreditEntry)
                        .creditHistory(newCreditHistory)
                        .validationResult(result)
                        .build();
  }

  @Override
  public PaymentOutput cancelPayment(final Payment payment, final CreditEntry creditEntry) {
    final ValidationResult result = payment.validatePayment();
    final CreditEntry updatedCreditEntry = addCreditEntry(payment, creditEntry);
    final CreditHistory newCreditHistory = updateCreditHistory(payment, CREDIT);
    final PaymentStatus updatedStatus = result.isPass() ? CANCELLED : FAILED;
    final Payment updatedPayment = payment.toBuilder()
                                          .paymentStatus(updatedStatus)
                                          .build();
    log.info("Payment cancellation is {} for order id: {}", updatedStatus, payment.getOrderId());
    return PaymentOutput.builder()
                        .payment(updatedPayment)
                        .creditEntry(updatedCreditEntry)
                        .creditHistory(newCreditHistory)
                        .validationResult(result)
                        .build();
  }

  private ValidationResult validateCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    final ValidationResult result;
    if (payment.getValue()
               .isGreaterThan(creditEntry.getTotalCredit())) {
      log.error("Customer with id: {} doesn't have enough credit for payment! Required {}, Actual {}",
                payment.getCustomer()
                       .getId(),
                payment.getValue(),
                creditEntry.getTotalCredit());
      result = fail("Customer with id %s  doesn't have enough credit for payment!",
                    payment.getCustomer()
                           .getId());
    } else {
      result = pass();
    }

    return result;
  }

  private CreditEntry subtractCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    return creditEntry.subtractCredit(payment.getValue());
  }

  private CreditHistory updateCreditHistory(final Payment payment, final TransactionType transactionType) {
    return CreditHistory.builder()
                        .customer(payment.getCustomer())
                        .credit(payment.getValue())
                        .transactionType(transactionType)
                        .creditHistoryEventType(PAYMENT)
                        .build();
  }

  private ValidationResult validateCreditHistory(final CreditEntry creditEntry,
                                                 final List<CreditHistory> creditHistories) {
    final CashValue totalCreditHistory = getTotalHistoryAmount(creditHistories, CREDIT);
    final CashValue totalDebitHistory = getTotalHistoryAmount(creditHistories, DEBIT);

    final ValidationResultBuilder validationResult = ValidationResult.builder();

    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
      log.error("Customer with id: {} doesn't have enough credit according to credit history", creditEntry.getId());


      validationResult.failure("Customer with id %s doesn't have enough credit according to credit history",
                               creditEntry.getId());
    }

    if (!creditEntry.getTotalCredit()
                    .equals(totalCreditHistory.subtract(totalDebitHistory))) {
      log.error("Credit history total is not equal to current credit for customer id: {}! According Credit Entry: {}, "
                + "According Credit History: {}",
                creditEntry.getId(),
                creditEntry.getTotalCredit(),
                totalCreditHistory.subtract(totalDebitHistory));
      validationResult.failure("Credit history total is not equal to current credit for customer id: %s",
                               creditEntry.getId());
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

  private CreditEntry addCreditEntry(final Payment payment, final CreditEntry creditEntry) {
    return creditEntry.addCredit(payment.getValue());
  }
}
