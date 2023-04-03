package com.acroteq.food.ordering.system.restaurant.service.domain.ports.input.message.listener;

import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequestDto restaurantApprovalRequest);
}
