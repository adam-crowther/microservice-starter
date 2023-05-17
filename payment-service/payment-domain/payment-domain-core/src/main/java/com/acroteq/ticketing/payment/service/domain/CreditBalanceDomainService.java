package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceOutput;

import java.util.List;

public interface CreditBalanceDomainService {

  CreditBalanceOutput createNewCreditBalance(Customer customer);

  CreditBalanceOutput updateCreditLimit(CashValue newCreditLimit,
                                        CashValue oldCreditLimit,
                                        CreditBalance oldCreditBalance,
                                        List<CreditChange> creditHistory);
}
