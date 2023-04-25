package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreditEntryOutput {

  CreditEntry creditEntry;
  CreditHistory creditHistory;
}
