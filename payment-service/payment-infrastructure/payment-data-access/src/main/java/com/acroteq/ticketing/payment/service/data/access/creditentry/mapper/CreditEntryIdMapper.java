package com.acroteq.ticketing.payment.service.data.access.creditentry.mapper;

import com.acroteq.ticketing.application.mapper.id.IdMapper;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditEntryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CreditEntryIdMapper extends IdMapper<CreditEntryId> {

  @Mapping(target = "value", source = "id")
  @Override
  CreditEntryId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  CreditEntryId convertLongToId(Long id);
}
