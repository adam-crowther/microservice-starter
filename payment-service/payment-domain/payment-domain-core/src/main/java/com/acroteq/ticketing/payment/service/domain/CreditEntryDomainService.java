package com.acroteq.ticketing.payment.service.domain;

import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryOutput;

import java.util.List;

public interface CreditEntryDomainService {

  CreditEntryOutput createCreditEntry(CreditEntry newCredit);

  CreditEntryOutput updateCreditEntry(CreditEntry updatedCredit,
                                      CreditEntry currentCredit,
                                      List<CreditHistory> creditHistoryList);
}
