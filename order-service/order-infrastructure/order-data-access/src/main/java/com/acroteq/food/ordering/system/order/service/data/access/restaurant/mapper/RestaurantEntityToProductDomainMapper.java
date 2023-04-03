package com.acroteq.food.ordering.system.order.service.data.access.restaurant.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class })
public interface RestaurantEntityToProductDomainMapper {

  @Mapping(target = "id", source = "productId")
  @Mapping(target = "name", source = "productName")
  @Mapping(target = "price.currencyId", source = "productPriceCurrencyId")
  @Mapping(target = "price.amount", source = "productPriceAmount")
  Product convertEntityToDomain(final RestaurantEntity restaurantEntity);
}
