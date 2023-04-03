package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.entity.RestaurantEntity;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.exception.NoRestaurantsFoundException;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.OrderDetail;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Product;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(uses = IdMapper.class)
public abstract class RestaurantEntityToDomainMapper {

  public Restaurant convertEntityToDomain(final List<RestaurantEntity> restaurantEntities) {
    final RestaurantEntity restaurantEntity =
        restaurantEntities.stream()
                          .findFirst()
                          .orElseThrow(NoRestaurantsFoundException::new);

    final List<Product> restaurantProducts = restaurantEntities.stream()
                                                               .map(this::convertProductEntityToDomain)
                                                               .collect(toList());

    final OrderDetail orderDetail = OrderDetail.builder()
                                               .products(restaurantProducts)
                                               .build();

    return Restaurant.builder()
                     .id(RestaurantId.of(restaurantEntity.getRestaurantId()))
                     .orderDetail(orderDetail)
                     .active(restaurantEntity.getRestaurantActive())
                     .build();
  }

  @Mapping(target = "id", source = "productId")
  @Mapping(target = "name", source = "productName")
  @Mapping(target = "price.currencyId", source = "productPriceCurrencyId")
  @Mapping(target = "price.amount", source = "productPriceAmount")
  @Mapping(target = "available", source = "productAvailable")
  @Mapping(target = "quantity", ignore = true)
  abstract Product convertProductEntityToDomain(final RestaurantEntity entity);
}
