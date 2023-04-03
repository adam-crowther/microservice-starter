package com.acroteq.food.ordering.system.order.service.domain.resolver;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RestaurantResolver {

  private final RestaurantProductRepository restaurantProductRepository;

  public Restaurant resolve(final RestaurantId id) {
    return restaurantProductRepository.loadRestaurant(id)
                                      .orElseThrow(() -> new RestaurantNotFoundException(id));
  }

  public Restaurant resolve(final UUID id) {
    final RestaurantId restaurantId = RestaurantId.of(id);
    return restaurantProductRepository.loadRestaurant(restaurantId)
                                      .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }
}
