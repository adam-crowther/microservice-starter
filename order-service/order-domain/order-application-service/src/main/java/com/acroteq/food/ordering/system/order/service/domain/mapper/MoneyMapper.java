package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.domain.valueobject.Money;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.CashValue;
import org.mapstruct.Mapper;

@Mapper(uses = {CurrencyMapper.class})
public interface MoneyMapper {

  Money convert(CashValue dto);
}
