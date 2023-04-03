package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderItem extends BaseEntity<OrderItemId> {

  private OrderId orderId;
  @NonNull private final Product product;
  private final int quantity;

  public CashValue getPrice() {
    return product.getPrice();
  }

  public CashValue getSubTotal() {
    return product.getPrice()
                  .multiply(quantity);
  }

  void initialiseOrderItem(final OrderId orderId, final OrderItemId orderItemId) {
    this.orderId = orderId;
    setId(orderItemId);
  }

  void validateOrderItem() {
    product.validatePrice();
  }
}
