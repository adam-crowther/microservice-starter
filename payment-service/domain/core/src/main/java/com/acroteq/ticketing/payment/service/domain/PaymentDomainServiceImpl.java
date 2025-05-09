package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.domain.validation.ValidationResult.fail;
import static com.acroteq.domain.validation.ValidationResult.pass;
import static com.acroteq.domain.valueobject.CashValue.ZERO;
import static com.acroteq.domain.valueobject.PaymentStatus.CANCELLED;
import static com.acroteq.domain.valueobject.PaymentStatus.COMPLETED;
import static com.acroteq.domain.valueobject.PaymentStatus.FAILED;
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.PAYMENT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;

import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.validation.ValidationResultBuilder;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.valueobject.PaymentOutput;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {

  @Override
  public PaymentOutput validatePayment(
      final Payment payment, final CreditBalance originalCreditBalance,
      final List<CreditChange> creditHistory) {
    final ValidationResultBuilder resultBuilder = ValidationResult.builder();
    resultBuilder.validationResult(payment.validate());
    resultBuilder.validationResult(checkCreditBalance(payment, originalCreditBalance));

    final CreditBalance updatedCreditBalance = subtractCredit(payment, originalCreditBalance);
    final CreditChange newCreditChange = createNewCreditChange(payment, DEBIT);

    final List<CreditChange> updatedCreditHistory = ImmutableList.<CreditChange>builder()
                                                                 .addAll(creditHistory)
                                                                 .add(newCreditChange)
                                                                 .build();
    final Customer customer = payment.getCustomer();

    resultBuilder.validationResult(validateCreditHistory(customer, updatedCreditBalance, updatedCreditHistory));

    final ValidationResult result = resultBuilder.build();
    final PaymentStatus updatedStatus = getPaymentStatus(result);
    final Payment updatedPayment = payment.toBuilder()
                                          .status(updatedStatus)
                                          .build();

    log.info("Payment is {} for order id: {}", updatedStatus, payment.getOrderId());
    return PaymentOutput.builder()
                        .payment(updatedPayment)
                        .validationResult(result)
                        .originalCreditBalance(originalCreditBalance)
                        .updatedCreditBalance(updatedCreditBalance)
                        .newCreditChange(newCreditChange)
                        .build();
  }

  private PaymentStatus getPaymentStatus(final ValidationResult result) {
    return result.isPass() ? COMPLETED : FAILED;
  }

  @Override
  public PaymentOutput cancelPayment(final Payment payment, final CreditBalance originalCreditBalance) {
    final ValidationResult result = payment.cancel();
    final CreditBalance updatedCreditBalance = addCreditBalance(payment, originalCreditBalance);
    final CreditChange newCreditChange = createNewCreditChange(payment, CREDIT);
    final PaymentStatus updatedStatus = getCancellationStatus(result);
    final Payment updatedPayment = payment.toBuilder()
                                          .status(updatedStatus)
                                          .build();
    log.info("Payment cancellation is {} for order id: {}", updatedStatus, payment.getOrderId());
    return PaymentOutput.builder()
                        .payment(updatedPayment)
                        .validationResult(result)
                        .originalCreditBalance(originalCreditBalance)
                        .updatedCreditBalance(updatedCreditBalance)
                        .newCreditChange(newCreditChange)
                        .build();

  }

  private PaymentStatus getCancellationStatus(final ValidationResult result) {
    return result.isPass() ? CANCELLED : FAILED;
  }

  private ValidationResult checkCreditBalance(final Payment payment, final CreditBalance creditBalance) {
    final ValidationResult result;
    if (payment.getValue()
               .isGreaterThan(creditBalance.getTotalCredit())) {
      final Customer customer = payment.getCustomer();
      log.info("Customer with id: {} doesn't have enough credit for payment! Required {}, Actual {}",
               customer.getId(),
               payment.getValue(),
               creditBalance.getTotalCredit());
      result = fail("Customer with id %s  doesn't have enough credit for payment!", customer.getId());
    } else {
      result = pass();
    }

    return result;
  }

  private CreditBalance subtractCredit(final Payment payment, final CreditBalance creditBalance) {
    return creditBalance.subtractCredit(payment.getValue());
  }

  private CreditChange createNewCreditChange(final Payment payment, final TransactionType transactionType) {
    return CreditChange.builder()
                       .customer(payment.getCustomer())
                       .creditDelta(payment.getValue())
                       .transactionType(transactionType)
                       .creditChangeType(PAYMENT)
                       .build();
  }

  private ValidationResult validateCreditHistory(
      final Customer customer, final CreditBalance creditBalance,
      final List<CreditChange> creditHistory) {
    final CashValue totalCreditHistory = getTotalHistoryAmount(creditHistory, CREDIT);
    final CashValue totalDebitHistory = getTotalHistoryAmount(creditHistory, DEBIT);

    final ValidationResultBuilder validationResult = ValidationResult.builder();

    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
      log.info("Customer with id: {} doesn't have enough credit according to credit history", customer.getId());


      validationResult.failure("Customer with id %s doesn't have enough credit according to credit history",
                               customer.getId());
    }

    if (!creditBalance.getTotalCredit()
                      .equals(totalCreditHistory.subtract(totalDebitHistory))) {
      log.info("Credit history total is not equal to current credit for customer id: {}! According Credit Balance: {}, "
               + "According Credit History: {}",
               customer.getId(),
               creditBalance.getTotalCredit(),
               totalCreditHistory.subtract(totalDebitHistory));
      validationResult.failure("Credit history total is not equal to current credit for customer id: %s",
                               customer.getId());
    }

    return validationResult.build();
  }

  private CashValue getTotalHistoryAmount(
      final List<CreditChange> creditHistories,
      final TransactionType transactionType) {
    return creditHistories.stream()
                          .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                          .map(CreditChange::getCreditDelta)
                          .reduce(ZERO, CashValue::add);
  }

  private CreditBalance addCreditBalance(final Payment payment, final CreditBalance creditBalance) {
    return creditBalance.addCredit(payment.getValue());
  }
}
