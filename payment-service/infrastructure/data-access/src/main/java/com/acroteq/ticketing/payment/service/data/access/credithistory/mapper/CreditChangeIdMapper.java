package com.acroteq.ticketing.payment.service.data.access.credithistory.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.IdMapper;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface CreditChangeIdMapper extends IdMapper<CreditChangeId> {

  @Mapping(target = "value", source = "id")
  @Override
  CreditChangeId convertLongToId(Long id);

  @Mapping(target = "value", source = "id")
  @Override
  CreditChangeId convertStringToId(String id);
}
