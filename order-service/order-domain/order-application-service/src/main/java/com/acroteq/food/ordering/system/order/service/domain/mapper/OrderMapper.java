package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.resolver.RestaurantResolver;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(uses = {OrderItemMapper.class,
                CustomerMapper.class,
                RestaurantMapper.class,
                MoneyMapper.class,
                StreetAddressMapper.class})
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "streetAddress", source = "address")
  @Mapping(target = "trackingId", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "failureMessages", ignore = true)
  Order convert(CreateOrderCommand createOrderCommand);

  @Mapping(target = "id", source = "uuid")
  OrderId convertOrderId(UUID uuid);

  @Mapping(target = "id", source = "uuid")
  TrackingId convertTrackingId(UUID uuid);
}
