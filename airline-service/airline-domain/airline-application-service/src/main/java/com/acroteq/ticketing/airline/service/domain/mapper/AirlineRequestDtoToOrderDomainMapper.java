package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.airline.service.domain.resolver.AirlineResolver;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, OrderIdMapper.class, OrderItemDtoToDomainMapper.class, AirlineResolver.class })
public interface AirlineRequestDtoToOrderDomainMapper {

  @Mapping(target = "id", source = "orderId")
  @Mapping(target = "airline", source = "airlineId")
  Order convertDtoToDomain(AirlineApprovalRequestDto airlineApprovalRequest);
}
