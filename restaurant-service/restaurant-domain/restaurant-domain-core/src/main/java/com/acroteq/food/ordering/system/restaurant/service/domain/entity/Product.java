package com.acroteq.food.ordering.system.restaurant.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Product extends BaseEntity<ProductId> {

  private String name;
  private CashValue price;
  private final int quantity;
  private boolean available;

  public void updateWithConfirmedNamePriceAndAvailability(final String name,
                                                          final CashValue price,
                                                          final boolean available) {
    this.name = name;
    this.price = price;
    this.available = available;
  }
}
