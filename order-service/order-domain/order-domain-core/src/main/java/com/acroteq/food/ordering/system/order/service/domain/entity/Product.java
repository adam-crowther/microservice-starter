package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.Money;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.order.service.domain.precondition.OrderDomainPrecondition.checkPrecondition;

@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Product extends BaseEntity<ProductId> {

  @NonNull private final String name;
  @NonNull private final Money price;

  void validatePrice() {
    checkPrecondition(
        price.isGreaterThanZero(),
        "Product price must be greater than zero.");
  }

  public void validateNameAndPriceEquality(final Product other) {
    final String otherProductName = other.getName();
    checkPrecondition(name.equals(otherProductName),
                      "The product name %s does not match the given product's name %s",
                      name,
                      otherProductName);

    final Money otherProductPrice = other.getPrice();
    checkPrecondition(price.equals(otherProductPrice),
                      "The product price %s does not match the given product's price %s",
                      price,
                      otherProductPrice);
  }
}
