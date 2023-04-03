package com.acroteq.food.ordering.system.restaurant.service.domain;

import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant);
}
