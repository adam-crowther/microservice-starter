package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.resolver.AirlineResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, OrderIdMapper.class, OrderItemDtoToDomainMapper.class, AirlineResolver.class })
public interface OrderRequestDtoToOrderDomainMapper {

  @Mapping(target = "id", source = "orderId")
  @Mapping(target = "airline", source = "airlineId")
  Order convertDtoToDomain(AirlineApprovalRequestDto airlineApprovalRequest);
}