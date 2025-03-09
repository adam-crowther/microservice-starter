package com.acroteq.ticketing.payment.service.domain;

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.CREDIT_LIMIT_UPDATE;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT;
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceOutput;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;

import java.util.List;

public class CreditBalanceDomainServiceImpl implements CreditBalanceDomainService {

  @Override
  public CreditBalanceOutput createNewCreditBalance(final Customer customer) {
    final CreditBalance newCreditBalance = createCreditBalance(customer);

    final CashValue newTotalCredit = newCreditBalance.getTotalCredit();
    final CreditChange creditChange = CreditChange.builder()
                                                  .customer(customer)
                                                  .creditDelta(newTotalCredit)
                                                  .transactionType(CREDIT)
                                                  .creditChangeType(CREDIT_LIMIT_UPDATE)
                                                  .build();
    return CreditBalanceOutput.builder()
                              .creditBalance(newCreditBalance)
                              .creditChange(creditChange)
                              .build();
  }

  private CreditBalance createCreditBalance(final Customer customer) {
    return CreditBalance.builder()
                        .customer(customer)
                        .totalCredit(customer.getCreditLimit())
                        .build();
  }

  @Override
  public CreditBalanceOutput updateCreditLimit(final CashValue newCreditLimit,
                                               final CashValue oldCreditLimit,
                                               final CreditBalance currentCreditBalance,
                                               final List<CreditChange> creditHistory) {
    final CashValue creditLimitDelta = newCreditLimit.subtract(oldCreditLimit);
    final CashValue currentCredit = currentCreditBalance.getTotalCredit();
    final CashValue newCredit = currentCredit.add(creditLimitDelta);

    final CreditBalance creditBalance = currentCreditBalance.toBuilder()
                                                            .totalCredit(newCredit)
                                                            .build();

    final CreditChange previousChange = getPreviousChange(creditHistory);
    final TransactionType transactionType = creditLimitDelta.isGreaterThanOrEqualTo(ZERO) ? CREDIT : DEBIT;
    final CreditChange creditChange = previousChange.toBuilder()
                                                    .id(null)
                                                    .transactionType(transactionType)
                                                    .creditChangeType(CREDIT_LIMIT_UPDATE)
                                                    .creditDelta(creditLimitDelta.absolute())
                                                    .build();

    return CreditBalanceOutput.builder()
                              .creditBalance(creditBalance)
                              .creditChange(creditChange)
                              .build();
  }

  private CreditChange getPreviousChange(final List<CreditChange> creditHistory) {
    return creditHistory.get(creditHistory.size() - 1);
  }
}
