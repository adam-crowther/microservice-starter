package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotActiveException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static com.acroteq.food.ordering.system.precondition.Precondition.checkPrecondition;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

  @Override
  public OrderCreatedEvent validateAndInitiateOrder(final Order order, final Restaurant restaurant) {
    validateRestaurant(restaurant);
    validateOrderProducts(order, restaurant);

    order.validateOrder();
    order.initialiseOrder();

    log.info("Order {} is initialised.", order.getId());
    return OrderCreatedEvent.builder()
                            .order(order)
                            .build();
  }

  private void validateRestaurant(final Restaurant restaurant) {
    checkPrecondition(restaurant.isActive(), RestaurantNotActiveException::new, restaurant.getId());
  }

  private void validateOrderProducts(final Order order, final Restaurant restaurant) {
    final Map<ProductId, Product> productMap = restaurant.getProductMap();
    order.getItems()
         .stream()
         .map(OrderItem::getProduct)
         .forEach(validateOrderProduct(productMap));
  }

  private Consumer<Product> validateOrderProduct(final Map<ProductId, Product> productMap) {
    return p -> validateOrderProduct(productMap, p);
  }

  private void validateOrderProduct(final Map<ProductId, Product> productMap, final Product currentProduct) {
    Optional.of(currentProduct)
            .map(Product::getId)
            .map(productMap::get)
            .ifPresentOrElse(currentProduct::validateNameAndPriceEquality,
                             throwProductNotFoundException(currentProduct));
  }

  private Runnable throwProductNotFoundException(final Product currentProduct) {
    return () -> {
      throw new ProductNotFoundException(currentProduct.getId());
    };
  }

  @Override
  public OrderPaidEvent payOrder(final Order order) {
    order.pay();
    log.info("Order {} is paid", order.getId());
    return OrderPaidEvent.builder()
                         .order(order)
                         .build();
  }

  @Override
  public void approveOrder(final Order order) {
    order.approve();
    log.info("Order {} is approved", order.getId());
  }

  @Override
  public OrderCancelledEvent cancelOrderPayment(final Order order, final ValidationResult cancelResult) {
    order.initCancel(cancelResult);
    log.info("Order {} cancel requested", order.getId());
    return OrderCancelledEvent.builder()
                              .order(order)
                              .build();
  }

  @Override
  public void cancelOrder(final Order order, final ValidationResult cancelResult) {
    order.cancel(cancelResult);
    log.info("Order {} cancelled", order.getId());
  }
}
