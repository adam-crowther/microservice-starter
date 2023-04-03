package com.acroteq.food.ordering.system.order.service.domain.resolver;

import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductResolver {

  private final RestaurantProductRepository restaurantProductRepository;

  public Product resolve(final UUID uuid) {
    final ProductId productId = ProductId.of(uuid);
    return resolve(productId);
  }

  public Product resolve(final ProductId id) {
    return restaurantProductRepository.loadProduct(id)
                                      .orElseThrow(() -> new ProductNotFoundException(id));
  }
}
