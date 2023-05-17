package com.acroteq.ticketing.payment.service.data.access.creditbalance.mapper;

import com.acroteq.ticketing.application.mapper.id.IdMapper;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CreditBalanceIdMapper extends IdMapper<CreditBalanceId> {

  @Mapping(target = "value", source = "id")
  @Override
  CreditBalanceId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  CreditBalanceId convertLongToId(Long id);
}
