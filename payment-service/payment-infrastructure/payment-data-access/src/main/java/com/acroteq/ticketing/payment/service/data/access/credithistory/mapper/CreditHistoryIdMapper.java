package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.id.IdMapper;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreditHistoryIdMapper extends IdMapper<CreditHistoryId> {

  @Mapping(target = "value", source = "id")
  @Override
  CreditHistoryId convertLongToId(Long id);

  @Mapping(target = "value", source = "id")
  @Override
  CreditHistoryId convertStringToId(String id);
}
