package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.Money;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.order.service.domain.precondition.OrderDomainPrecondition.checkPrecondition;

@SuperBuilder
@Getter
@ToString(callSuper = true)
public class OrderItem extends BaseEntity<OrderItemId> {

  private OrderId orderId;
  @NonNull private final Product product;
  private final int quantity;

  public Money getPrice() {
    return product.getPrice();
  }

  public Money getSubTotal() {
    return product.getPrice()
                  .multiply(quantity);
  }

  void initialiseOrderItem(final OrderId orderId, final OrderItemId orderItemId) {
    this.orderId = orderId;
    setId(orderItemId);
  }

  void validateOrderItem() {
    checkPrecondition(id != null, "OrderItemId can not be null");
    checkPrecondition(orderId != null, "OrderId can not be null");
    product.validatePrice();
  }
}
