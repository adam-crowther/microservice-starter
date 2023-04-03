package com.acroteq.food.ordering.system.restaurant.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.OrderApproval;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.exception.OrderApprovalSaveFailedException;
import com.acroteq.food.ordering.system.restaurant.service.domain.exception.RestaurantNotFoundException;
import com.acroteq.food.ordering.system.restaurant.service.domain.mapper.RestaurantDataDtoToDomainMapper;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantApprovalRequestHelper {

  private final RestaurantDomainService restaurantDomainService;
  private final RestaurantDataDtoToDomainMapper restaurantDataMapper;
  private final RestaurantRepository restaurantRepository;
  private final OrderApprovalRepository orderApprovalRepository;

  @Transactional
  public OrderApprovalEvent persistOrderApproval(final RestaurantApprovalRequestDto restaurantApprovalRequest) {
    log.info("Processing restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
    final Restaurant restaurant = findRestaurant(restaurantApprovalRequest);
    final OrderApprovalEvent orderApprovalEvent =
        restaurantDomainService.validateOrder(restaurant);
    final OrderApproval orderApproval = restaurant.getOrderApproval();
    final OrderApproval savedOrderApproval = orderApprovalRepository.save(orderApproval);
    if (savedOrderApproval == null) {
      throw new OrderApprovalSaveFailedException(orderApproval.getId());
    }

    return orderApprovalEvent;
  }

  private Restaurant findRestaurant(final RestaurantApprovalRequestDto restaurantApprovalRequest) {
    final Restaurant restaurant = restaurantDataMapper
        .convertDtoToDomain(restaurantApprovalRequest);
    final Optional<Restaurant> restaurantResult = restaurantRepository.findRestaurantInformation(restaurant);
    if (restaurantResult.isEmpty()) {
      log.error("Restaurant with id " + restaurant.getId() + " not found!");
      throw new RestaurantNotFoundException(restaurant.getId());
    }

    final Restaurant restaurantEntity = restaurantResult.get();
    restaurant.setActive(restaurantEntity.isActive());
    restaurant.getOrderDetail()
              .getProducts()
              .forEach(product ->
                           restaurantEntity.getOrderDetail()
                                           .getProducts()
                                           .forEach(p -> {
                                             if (p.getId()
                                                  .equals(product.getId())) {
                                               product.updateWithConfirmedNamePriceAndAvailability(p.getName(),
                                                                                                   p.getPrice(),
                                                                                                   p.isAvailable());
                                             }
                                           }));
    restaurant.getOrderDetail()
              .setId(OrderId.of(UUID.fromString(restaurantApprovalRequest.getOrderId())));

    return restaurant;
  }
}
