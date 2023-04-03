package com.acroteq.food.ordering.system.order.service.data.access.restaurant.mapper;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.exception.RestaurantDataAccessException;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.acroteq.food.ordering.system.helper.StreamHelper.toSingleItem;

/**
 * We only use MapStruct where it simplifies things.  In this case it's easier to just make a component.
 */
@RequiredArgsConstructor
@Component
public class RestaurantEntityToDomainMapper {

  private final RestaurantEntityToProductDomainMapper restaurantEntityToProductDomainMapper;

  public Restaurant convertEntityToDomain(final List<RestaurantEntity> restaurants) {
    final RestaurantId restaurantId = restaurants.stream()
                                                 .map(RestaurantEntity::getRestaurantId)
                                                 .distinct()
                                                 .reduce(toSingleItem())
                                                 .map(RestaurantId::of)
                                                 .orElseThrow(RestaurantDataAccessException::new);

    final boolean restaurantActive = restaurants.stream()
                                                .allMatch(RestaurantEntity::isRestaurantActive);

    final List<Product> products = restaurants.stream()
                                              .map(restaurantEntityToProductDomainMapper::convertEntityToDomain)
                                              .toList();

    return Restaurant.builder()
                     .id(restaurantId)
                     .active(restaurantActive)
                     .products(products)
                     .build();
  }
}
