package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.domain.exception.InvalidPriceException;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNameMismatchException;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductPriceMismatchException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.acroteq.food.ordering.system.precondition.Precondition.checkPrecondition;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Product extends BaseEntity<ProductId> {

  @NonNull private final String name;
  @NonNull private final CashValue price;

  void validatePrice() {
    checkPrecondition(price.isGreaterThanZero(), InvalidPriceException::new);
  }

  public void validateNameAndPriceEquality(final Product other) {
    final String otherProductName = other.getName();
    checkPrecondition(name.equals(otherProductName),
                      ProductNameMismatchException::new,
                      name,
                      otherProductName);

    final CashValue otherProductPrice = other.getPrice();
    checkPrecondition(price.equals(otherProductPrice),
                      ProductPriceMismatchException::new,
                      price,
                      otherProductPrice);
  }
}
