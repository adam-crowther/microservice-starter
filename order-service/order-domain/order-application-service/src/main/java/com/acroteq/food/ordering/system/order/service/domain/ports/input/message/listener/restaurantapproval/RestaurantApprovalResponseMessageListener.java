package com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import org.mapstruct.Mapper;

public interface RestaurantApprovalResponseMessageListener {

  void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

  void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
