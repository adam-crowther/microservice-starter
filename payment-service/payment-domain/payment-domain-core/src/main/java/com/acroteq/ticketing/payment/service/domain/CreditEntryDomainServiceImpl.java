package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType.CREDIT_LIMIT_CHANGE;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.exception.CreditEntryWithNullCustomerIdException;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryOutput;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;

import java.util.List;
import java.util.Optional;

public class CreditEntryDomainServiceImpl implements CreditEntryDomainService {

  @Override
  public CreditEntryOutput createCreditEntry(final CreditEntry newCredit) {
    final CashValue newTotalCredit = newCredit.getTotalCredit();
    final CustomerId customerId = Optional.of(newCredit)
                                          .map(CreditEntry::getCustomer)
                                          .map(Customer::getId)
                                          .orElseThrow(CreditEntryWithNullCustomerIdException::new);
    final CreditHistory creditHistory = CreditHistory.builder()
                                                     .customerId(customerId)
                                                     .credit(newTotalCredit)
                                                     .transactionType(CREDIT)
                                                     .creditHistoryEventType(CREDIT_LIMIT_CHANGE)
                                                     .build();
    return CreditEntryOutput.builder()
                            .creditEntry(newCredit)
                            .creditHistory(creditHistory)
                            .build();
  }

  @Override
  public CreditEntryOutput updateCreditEntry(final CreditEntry currentCredit,
                                             final CreditEntry updatedCredit,
                                             final List<CreditHistory> creditHistoryList) {
    final CashValue creditLimitDelta = calculateCreditLimitDelta(updatedCredit, creditHistoryList);
    final CashValue currentTotalCredit = currentCredit.getTotalCredit();
    final CashValue newTotalCredit = currentTotalCredit.add(creditLimitDelta);

    final CreditEntry creditEntry = updatedCredit.toBuilder()
                                                 .totalCredit(newTotalCredit)
                                                 .build();

    final CreditHistory previousHistory = getPreviousHistory(creditHistoryList);
    final TransactionType transactionType = creditLimitDelta.isGreaterThanOrEqualToZero() ? CREDIT : DEBIT;
    final CreditHistory creditHistory = previousHistory.toBuilder()
                                                       .id(null)
                                                       .transactionType(transactionType)
                                                       .credit(creditLimitDelta.absolute())
                                                       .build();

    return CreditEntryOutput.builder()
                            .creditEntry(creditEntry)
                            .creditHistory(creditHistory)
                            .build();
  }

  private CashValue calculateCreditLimitDelta(final CreditEntry update, final List<CreditHistory> creditHistoryList) {
    final CashValue previousCreditLimit = calculatePreviousCreditLimit(creditHistoryList);
    final CashValue newCreditLimit = update.getTotalCredit();
    return newCreditLimit.subtract(previousCreditLimit);
  }

  private CashValue calculatePreviousCreditLimit(final List<CreditHistory> creditHistoryList) {
    return creditHistoryList.stream()
                            .filter(CreditHistory::isCreditLimitChange)
                            .map(this::getCreditWithSign)
                            .reduce(ZERO, CashValue::add);
  }

  private CreditHistory getPreviousHistory(final List<CreditHistory> creditHistoryList) {
    return creditHistoryList.get(creditHistoryList.size() - 1);
  }

  private CashValue getCreditWithSign(final CreditHistory creditHistory) {
    final CashValue credit = creditHistory.getCredit();
    final int sign = creditHistory.getTransactionType() == CREDIT ? 1 : -1;
    return credit.multiply(sign);
  }
}
