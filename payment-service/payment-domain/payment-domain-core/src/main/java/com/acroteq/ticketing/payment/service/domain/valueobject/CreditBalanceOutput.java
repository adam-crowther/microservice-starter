package com.acroteq.ticketing.payment.service.domain.valueobject;

import com.acroteq.ticketing.payment.service.domain.entity.CreditBalance;
import com.acroteq.ticketing.payment.service.domain.entity.CreditChange;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class CreditBalanceOutput {

  @NonNull CreditBalance creditBalance;
  @NonNull CreditChange creditChange;
}
