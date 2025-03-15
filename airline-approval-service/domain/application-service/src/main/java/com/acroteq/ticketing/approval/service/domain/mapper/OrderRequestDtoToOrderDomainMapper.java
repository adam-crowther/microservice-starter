package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.resolver.AirlineResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class, OrderIdMapper.class, OrderItemDtoToDomainMapper.class, AirlineResolver.class })
public interface OrderRequestDtoToOrderDomainMapper extends DtoToDomainMapper<AirlineApprovalRequestDto, Order> {

  @Mapping(target = "id", source = "orderId")
  @Mapping(target = "version", source = "orderVersion")
  @Mapping(target = "airline", source = "airlineId")
  @Override
  Order convertDtoToDomain(AirlineApprovalRequestDto airlineApprovalRequest);
}
