package com.acroteq.food.ordering.system.order.service.domain.test.helper;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.lenient;

@TestComponent
public class RestaurantTestDataHelper {

  public static final UUID RESTAURANT_ID = UUID.fromString("122db46e-6e1f-4d51-987c-558d27dd6cd1");

  public static final Restaurant RESTAURANT = Restaurant.builder()
                                                        .id(RestaurantId.of(RESTAURANT_ID))
                                                        .active(true)
                                                        .products(List.of(new Product[]{ ProductTestDataHelper.PRODUCT_1,
                                                                                         ProductTestDataHelper.PRODUCT_2,
                                                                                         ProductTestDataHelper.PRODUCT_3 }))
                                                        .build();

  @Autowired
  public RestaurantTestDataHelper(final RestaurantProductRepository restaurantProductRepository) {
    this.restaurantProductRepository = restaurantProductRepository;
  }

  private final RestaurantProductRepository restaurantProductRepository;
  public void initialiseMocks() {
    lenient().when(restaurantProductRepository.loadRestaurant(RestaurantId.of(RESTAURANT_ID)))
             .thenReturn(Optional.of(RESTAURANT));
  }
}
