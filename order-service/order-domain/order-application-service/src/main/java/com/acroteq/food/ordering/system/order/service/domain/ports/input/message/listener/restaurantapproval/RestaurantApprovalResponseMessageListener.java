package com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseDto;

public interface RestaurantApprovalResponseMessageListener {

  void orderApproved(RestaurantApprovalResponseDto restaurantApprovalResponse);

  void orderRejected(RestaurantApprovalResponseDto restaurantApprovalResponse);
}
