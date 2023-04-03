package com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantEntityId implements Serializable {

  private UUID restaurantId;

  private UUID productId;
}
