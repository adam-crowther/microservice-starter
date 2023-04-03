package com.acroteq.food.ordering.system.order.service.data.access.restaurant.repository;

import com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {

  Optional<List<RestaurantEntity>> findByRestaurantId(UUID restaurantId);

  Optional<RestaurantEntity> findByProductId(UUID restaurantId);
}
