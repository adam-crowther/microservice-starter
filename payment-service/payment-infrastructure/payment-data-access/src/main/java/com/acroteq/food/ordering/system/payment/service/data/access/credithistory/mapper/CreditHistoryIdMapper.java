package com.acroteq.food.ordering.system.payment.service.data.access.credithistory.mapper;

import com.acroteq.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface CreditHistoryIdMapper {

  @Mapping(target = "value", source = "id")
  CreditHistoryId convertUuidToCreditHistoryId(final UUID id);

  default UUID convertCreditHistoryIdToUuid(final CreditHistoryId creditHistoryId) {
    return creditHistoryId.getValue();
  }
}
