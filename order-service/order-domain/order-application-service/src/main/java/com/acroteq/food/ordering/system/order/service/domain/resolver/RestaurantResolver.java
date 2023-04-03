package com.acroteq.food.ordering.system.order.service.domain.resolver;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class RestaurantResolver {

  private final RestaurantRepository restaurantRepository;

  public Restaurant resolve(final RestaurantId id) {
    return restaurantRepository.loadRestaurant(id)
                               .orElseThrow(() -> new RestaurantNotFoundException(id));
  }

  public Restaurant resolve(final UUID id) {
    final RestaurantId restaurantId = RestaurantId.of(id);
    return restaurantRepository.loadRestaurant(restaurantId)
                               .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }
}
