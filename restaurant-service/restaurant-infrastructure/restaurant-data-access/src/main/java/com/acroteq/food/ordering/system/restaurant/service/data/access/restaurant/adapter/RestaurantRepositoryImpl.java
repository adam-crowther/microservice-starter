package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.adapter;

import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.entity.RestaurantEntity;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper.RestaurantEntityToDomainMapper;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.repository.RestaurantJpaRepository;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantEntityToDomainMapper restaurantEntityToDomainMapper;

  public RestaurantRepositoryImpl(final RestaurantJpaRepository restaurantJpaRepository,
                                  final RestaurantEntityToDomainMapper restaurantEntityToDomainMapper) {
    this.restaurantJpaRepository = restaurantJpaRepository;
    this.restaurantEntityToDomainMapper = restaurantEntityToDomainMapper;
  }

  @Override
  public Optional<Restaurant> findRestaurantInformation(final Restaurant restaurant) {
    final List<UUID> restaurantProducts = getRestaurantProductIds(restaurant);
    final Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository
        .findByRestaurantIdAndProductIdIn(restaurant.getId()
                                                    .getValue(),
                                          restaurantProducts);
    return restaurantEntities.map(restaurantEntityToDomainMapper::convertEntityToDomain);
  }

  private List<UUID> getRestaurantProductIds(final Restaurant restaurant) {
    return restaurant.getOrderDetail()
                     .getProducts()
                     .stream()
                     .map(product -> product.getId()
                                            .getValue())
                     .collect(Collectors.toList());
  }
}
