package com.acroteq.food.ordering.system.order.service.domain.test.helper;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.OrderItem;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.acroteq.food.ordering.system.order.service.domain.test.helper.CustomerTestDataHelper.CUSTOMER_ID;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.RestaurantTestDataHelper.RESTAURANT_ID;
import static org.mockito.Mockito.lenient;

@TestComponent
public class OrderTestDataHelper {

  public static final UUID ADDRESS_ID = UUID.fromString("bdec7844-8841-45c4-8fec-deef477ef863");
  public static final String ADDRESS_STREET = "ADDRESS_STREET";
  public static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
  public static final String ADDRESS_CITY = "ADDRESS_CITY";
  public static final StreetAddress ADDRESS = StreetAddress.builder()
                                                           .id(ADDRESS_ID)
                                                           .street(ADDRESS_STREET)
                                                           .postalCode(ADDRESS_POSTAL_CODE)
                                                           .city(ADDRESS_CITY)
                                                           .build();

  public static final UUID ORDER_1_ID = UUID.fromString("07b87bfc-ab98-4dfd-81f0-159694fdaa2d");
  public static final UUID ORDER_1_TRACKING_ID = UUID.fromString("ebdda0f6-6c42-432f-90e6-4ad80bb2b77f");
  public static final UUID ORDER_2_ID = UUID.fromString("3d2217fe-37ce-47bb-abf4-097b5897ddc4");
  public static final UUID ORDER_2_TRACKING_ID = UUID.fromString("ef8bfcc7-7704-45f2-89a2-666db5c332da");
  public static final UUID ORDER_3_ID = UUID.fromString("c4dddb7f-15e8-4664-a82b-e928da58eb81");
  public static final UUID ORDER_3_TRACKING_ID = UUID.fromString("3404c2d3-3ed3-412c-a83f-704142b3aaf6");

  public static final Long ORDER_1_ITEM_1_ID = 1L;
  public static final Integer ORDER_1_ITEM_1_QUANTITY = 11;
  public static final OrderItem ORDER_1_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_1_ITEM_1_ID))
                                                          .orderId(OrderId.of(ORDER_1_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_1)
                                                          .quantity(ORDER_1_ITEM_1_QUANTITY)
                                                          .build();
  public static final Order ORDER_1 = createOrder(ORDER_1_ID, ORDER_1_TRACKING_ID, ORDER_1_ITEM_1);

  public static final Long ORDER_2_ITEM_1_ID = 1L;
  public static final Integer ORDER_2_ITEM_1_QUANTITY = 21;
  public static final OrderItem ORDER_2_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_2_ITEM_1_ID))
                                                          .orderId(OrderId.of(ORDER_2_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_2)
                                                          .quantity(ORDER_2_ITEM_1_QUANTITY)
                                                          .build();
  public static final Long ORDER_2_ITEM_2_ID = 2L;
  public static final Integer ORDER_2_ITEM_2_QUANTITY = 22;
  public static final OrderItem ORDER_2_ITEM_2 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_2_ITEM_2_ID))
                                                          .orderId(OrderId.of(ORDER_2_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_2)
                                                          .quantity(ORDER_2_ITEM_2_QUANTITY)
                                                          .build();
  public static final Order ORDER_2 = createOrder(ORDER_2_ID, ORDER_2_TRACKING_ID, ORDER_2_ITEM_1, ORDER_2_ITEM_2);

  public static final Long ORDER_3_ITEM_1_ID = 1L;
  public static final Integer ORDER_3_ITEM_1_QUANTITY = 31;
  public static final OrderItem ORDER_3_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_1_ID))
                                                          .orderId(OrderId.of(ORDER_3_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_3)
                                                          .quantity(ORDER_3_ITEM_1_QUANTITY)
                                                          .build();
  public static final Long ORDER_3_ITEM_2_ID = 2L;
  public static final Integer ORDER_3_ITEM_2_QUANTITY = 32;
  public static final OrderItem ORDER_3_ITEM_2 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_2_ID))
                                                          .orderId(OrderId.of(ORDER_3_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_3)
                                                          .quantity(ORDER_3_ITEM_2_QUANTITY)
                                                          .build();
  public static final Long ORDER_3_ITEM_3_ID = 3L;
  public static final Integer ORDER_3_ITEM_3_QUANTITY = 33;
  public static final OrderItem ORDER_3_ITEM_3 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_3_ID))
                                                          .orderId(OrderId.of(ORDER_3_ID))
                                                          .product(ProductTestDataHelper.PRODUCT_3)
                                                          .quantity(ORDER_3_ITEM_3_QUANTITY)
                                                          .build();
  public static final Order ORDER_3 = createOrder(ORDER_3_ID,
                                                  ORDER_3_TRACKING_ID,
                                                  ORDER_3_ITEM_1,
                                                  ORDER_3_ITEM_2,
                                                  ORDER_3_ITEM_3);

  private final OrderRepository orderRepository;

  @Autowired
  public OrderTestDataHelper(final OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public static Order createOrder(final UUID orderId, final UUID trackingId, final OrderItem... items) {
    return Order.builder()
                .id(OrderId.of(orderId))
                .trackingId(TrackingId.of(trackingId))
                .customerId(CustomerId.of(CUSTOMER_ID))
                .restaurantId(RestaurantId.of(RESTAURANT_ID))
                .streetAddress(ADDRESS)
                .items(List.of(items))
                .build();
  }

  public void initialiseMocks() {
    initialiseMocks(ORDER_1_TRACKING_ID, ORDER_1);
    initialiseMocks(ORDER_2_TRACKING_ID, ORDER_2);
    initialiseMocks(ORDER_3_TRACKING_ID, ORDER_3);
  }

  private void initialiseMocks(final UUID trackingId, final Order order) {
    lenient().when(orderRepository.findByTrackingId(TrackingId.of(trackingId)))
             .thenReturn(Optional.of(order));
    lenient().when(orderRepository.save(order))
             .thenReturn(order);
  }
}
