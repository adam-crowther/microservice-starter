package com.acroteq.food.ordering.system.order.service.domain.ports.output.repository;

import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface ProductRepository {

  Optional<Product> loadProduct(ProductId productId);
}
