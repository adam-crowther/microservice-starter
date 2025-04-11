package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.domain.valueobject.CashValue;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public interface CashValueMapper {

  com.acroteq.ticketing.airline.service.presentation.model.CashValue convert(CashValue cashValue);

  CashValue convert(com.acroteq.ticketing.airline.service.presentation.model.CashValueCommand cashValue);
}
