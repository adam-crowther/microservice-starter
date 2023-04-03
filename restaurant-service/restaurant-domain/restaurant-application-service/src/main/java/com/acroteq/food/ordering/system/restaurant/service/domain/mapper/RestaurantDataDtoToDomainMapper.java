package com.acroteq.food.ordering.system.restaurant.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class })
public interface RestaurantDataDtoToDomainMapper {


  @Mapping(target = "id", source = "restaurantId")
  @Mapping(target = "orderApproval", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "orderDetail.id", source = "orderId")
  @Mapping(target = "orderDetail.products", source = "products")
  @Mapping(target = "orderDetail.totalAmount.currencyId", source = "priceCurrencyId")
  @Mapping(target = "orderDetail.totalAmount.amount", source = "priceAmount")
  @Mapping(target = "orderDetail.orderStatus", source = "restaurantOrderStatus")
  Restaurant convertDtoToDomain(RestaurantApprovalRequestDto restaurantApprovalRequest);
}
