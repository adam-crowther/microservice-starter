package com.acroteq.food.ordering.system.restaurant.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import static com.acroteq.food.ordering.system.domain.valueobject.OrderApprovalStatus.APPROVED;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderApprovalStatus.REJECTED;

@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {

  @Override
  public OrderApprovalEvent validateOrder(final Restaurant restaurant) {
    final ValidationResult result = restaurant.validateOrder();
    log.info("Validating order with id: {}",
             restaurant.getOrderDetail()
                       .getId());

    if (result.isPass()) {
      log.info("Order is approved for order id: {}",
               restaurant.getOrderDetail()
                         .getId());
      restaurant.constructOrderApproval(APPROVED);
      return OrderApprovedEvent.builder()
                               .orderApproval(restaurant.getOrderApproval())
                               .restaurantId(restaurant.getId())
                               .result(result)
                               .build();
    } else {
      log.info("Order is rejected for order id: {}",
               restaurant.getOrderDetail()
                         .getId());
      restaurant.constructOrderApproval(REJECTED);
      return OrderRejectedEvent.builder()
                               .orderApproval(restaurant.getOrderApproval())
                               .restaurantId(restaurant.getId())
                               .result(result)
                               .build();
    }
  }
}
