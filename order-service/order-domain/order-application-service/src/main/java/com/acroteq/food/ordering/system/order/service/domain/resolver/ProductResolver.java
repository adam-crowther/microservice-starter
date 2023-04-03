package com.acroteq.food.ordering.system.order.service.domain.resolver;

import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductResolver {

  private final ProductRepository productRepository;

  public Product resolve(final UUID uuid) {
    final ProductId productId = ProductId.of(uuid);
    return resolve(productId);
  }

  public Product resolve(final ProductId id) {
    return productRepository.loadProduct(id)
                            .orElseThrow(() -> new ProductNotFoundException(id));
  }
}
