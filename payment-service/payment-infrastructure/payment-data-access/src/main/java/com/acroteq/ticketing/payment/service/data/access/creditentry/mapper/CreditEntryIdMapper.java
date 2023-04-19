package com.acroteq.ticketing.payment.service.data.access.creditentry.mapper;

import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreditEntryIdMapper {

  @Mapping(target = "value", source = "id")
  CreditEntryId convertUuidToCreditEntryId(Long id);

  default Long convertCreditEntryIdToUuId(final CreditEntryId creditEntryId) {
    return creditEntryId.getValue();
  }
}
