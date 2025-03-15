package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.domain.valueobject.CashValue;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public interface CashValueDtoToApiMapper {

  com.acroteq.ticketing.airline.service.presentation.model.CashValue convertApiToDto(CashValue cashValue);
}
