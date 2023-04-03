package com.acroteq.food.ordering.system.payment.service.data.access.creditentry.mapper;

import com.acroteq.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface CreditEntryIdMapper {

  @Mapping(target = "value", source = "id")
  CreditEntryId convertUuidToCreditEntryId(final UUID id);

  default UUID convertCreditEntryIdToUuid(final CreditEntryId creditEntryId) {
    return creditEntryId.getValue();
  }
}
