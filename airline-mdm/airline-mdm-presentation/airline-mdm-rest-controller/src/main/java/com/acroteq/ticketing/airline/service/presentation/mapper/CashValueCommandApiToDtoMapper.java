package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.presentation.model.CashValueCommand;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public interface CashValueCommandApiToDtoMapper {

  CashValue convertApiToDto(CashValueCommand cashValueCommand);
}
