package com.acroteq.food.ordering.system.restaurant.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.OrderApprovalStatus;
import com.acroteq.food.ordering.system.domain.valueobject.OrderStatus;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.restaurant.service.domain.valueobject.OrderApprovalId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Restaurant extends AggregateRoot<RestaurantId> {

  private OrderApproval orderApproval;
  private boolean active;
  private final OrderDetail orderDetail;

  public ValidationResult validateOrder() {
    final ValidationResult result = pass();
    if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
      result.addFailureMessage("Payment is not completed for order: %s", orderDetail.getId());
    }

    final CashValue
        totalAmount =
        orderDetail.getProducts()
                   .stream()
                   .map(product -> {
                     if (!product.isAvailable()) {
                       result.addFailureMessage("Product with id: %s is not available", product.getId());
                     }
                     return product.getPrice()
                                   .multiply(product.getQuantity());
                   })
                   .reduce(CashValue.ZERO, CashValue::add);

    if (!totalAmount.equals(orderDetail.getTotalAmount())) {
      result.addFailureMessage("Price total is not correct for order: %s", orderDetail.getId());
    }

    return result;
  }

  public void constructOrderApproval(final OrderApprovalStatus orderApprovalStatus) {
    this.orderApproval = OrderApproval.builder()
                                      .id(OrderApprovalId.random())
                                      .restaurantId(this.getId())
                                      .orderId(this.getOrderDetail()
                                                   .getId())
                                      .approvalStatus(orderApprovalStatus)
                                      .build();
  }

  public void setActive(final boolean active) {
    this.active = active;
  }
}
