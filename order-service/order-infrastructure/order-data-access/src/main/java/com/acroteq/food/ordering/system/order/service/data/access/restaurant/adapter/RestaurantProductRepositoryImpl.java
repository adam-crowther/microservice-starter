package com.acroteq.food.ordering.system.order.service.data.access.restaurant.adapter;

import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.mapper.RestaurantEntityToDomainMapper;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.mapper.RestaurantEntityToProductDomainMapper;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.repository.RestaurantJpaRepository;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RestaurantProductRepositoryImpl implements RestaurantProductRepository {

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantEntityToDomainMapper restaurantEntityMapper;
  private final RestaurantEntityToProductDomainMapper restaurantEntityToProductDomainMapper;

  @Override
  public Optional<Restaurant> loadRestaurant(final RestaurantId restaurantId) {
    final UUID id = restaurantId.getValue();

    return restaurantJpaRepository.findByRestaurantId(id)
                                  .map(restaurantEntityMapper::convertEntityToDomain);
  }

  @Override
  public Optional<Product> loadProduct(final ProductId productId) {
    final UUID id = productId.getValue();

    return restaurantJpaRepository.findByProductId(id)
                                  .map(restaurantEntityToProductDomainMapper::convertEntityToDomain);
  }
}
