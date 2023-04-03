package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Restaurant extends AggregateRoot<RestaurantId> {

  @NonNull
  @Builder.Default
  private final List<Product> products = new ArrayList<>();

  @Builder.Default
  private boolean active = false;

  public Map<ProductId, Product> getProductMap() {
    return products.stream()
                   .collect(toMap(Product::getId, identity()));
  }
}
