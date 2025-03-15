package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.ticketing.airline.service.presentation.model.CashValueCommand;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public interface CashValueCommandApiToDtoMapper {

  CashValue convertApiToDto(CashValueCommand cashValueCommand);
}
