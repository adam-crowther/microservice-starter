package com.acroteq.food.ordering.system.order.service.domain.ports.output.repository;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface RestaurantRepository {

  Optional<Restaurant> loadRestaurant(RestaurantId restaurantId);
}
