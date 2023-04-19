package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreditHistoryIdMapper {

  @Mapping(target = "value", source = "id")
  CreditHistoryId convertUuidToCreditHistoryId(Long id);

  default Long convertCreditHistoryIdToUuId(final CreditHistoryId creditHistoryId) {
    return creditHistoryId.getValue();
  }
}
