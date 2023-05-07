package com.acroteq.ticketing.order.service.domain.test.helper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TestComponent
public class OrderTestDataHelper {

  public static final String ADDRESS_STREET = "address-street";
  public static final String ADDRESS_POSTAL_CODE = "address-postal-code";
  public static final String ADDRESS_CITY = "address-city";
  public static final StreetAddress ADDRESS = StreetAddress.builder()
                                                           .street(ADDRESS_STREET)
                                                           .postalCode(ADDRESS_POSTAL_CODE)
                                                           .city(ADDRESS_CITY)
                                                           .build();

  public static final Long ORDER_1_ID = IdTestDataHelper.nextId();
  public static final UUID ORDER_1_TRACKING_ID = UUID.fromString("ebdda0f6-6c42-432f-90e6-4ad80bb2b77f");
  public static final Long ORDER_2_ID = IdTestDataHelper.nextId();
  public static final UUID ORDER_2_TRACKING_ID = UUID.fromString("ef8bfcc7-7704-45f2-89a2-666db5c332da");
  public static final Long ORDER_3_ID = IdTestDataHelper.nextId();
  public static final UUID ORDER_3_TRACKING_ID = UUID.fromString("3404c2d3-3ed3-412c-a83f-704142b3aaf6");

  public static final Long ORDER_1_ITEM_1_ID = 1L;
  public static final Integer ORDER_1_ITEM_1_QUANTITY = 11;
  public static final OrderItem ORDER_1_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_1_ITEM_1_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_1)
                                                          .quantity(ORDER_1_ITEM_1_QUANTITY)
                                                          .build();
  public static final Order ORDER_1 = createOrder(ORDER_1_ID, ORDER_1_TRACKING_ID, ORDER_1_ITEM_1);

  public static final Long ORDER_2_ITEM_1_ID = 1L;
  public static final Integer ORDER_2_ITEM_1_QUANTITY = 21;
  public static final OrderItem ORDER_2_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_2_ITEM_1_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_2)
                                                          .quantity(ORDER_2_ITEM_1_QUANTITY)
                                                          .build();
  public static final Long ORDER_2_ITEM_2_ID = 2L;
  public static final Integer ORDER_2_ITEM_2_QUANTITY = 22;
  public static final OrderItem ORDER_2_ITEM_2 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_2_ITEM_2_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_2)
                                                          .quantity(ORDER_2_ITEM_2_QUANTITY)
                                                          .build();
  public static final Order ORDER_2 = createOrder(ORDER_2_ID, ORDER_2_TRACKING_ID, ORDER_2_ITEM_1, ORDER_2_ITEM_2);

  public static final Long ORDER_3_ITEM_1_ID = 1L;
  public static final Integer ORDER_3_ITEM_1_QUANTITY = 31;
  public static final OrderItem ORDER_3_ITEM_1 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_1_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_3)
                                                          .quantity(ORDER_3_ITEM_1_QUANTITY)
                                                          .build();
  public static final Long ORDER_3_ITEM_2_ID = 2L;
  public static final Integer ORDER_3_ITEM_2_QUANTITY = 32;
  public static final OrderItem ORDER_3_ITEM_2 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_2_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_3)
                                                          .quantity(ORDER_3_ITEM_2_QUANTITY)
                                                          .build();
  public static final Long ORDER_3_ITEM_3_ID = 3L;
  public static final Integer ORDER_3_ITEM_3_QUANTITY = 33;
  public static final OrderItem ORDER_3_ITEM_3 = OrderItem.builder()
                                                          .id(OrderItemId.of(ORDER_3_ITEM_3_ID))
                                                          .flight(FlightTestDataHelper.FLIGHT_3)
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

  public static Order createOrder(final Long orderId, final UUID trackingId, final OrderItem... items) {
    return Order.builder()
                .id(OrderId.of(orderId))
                .trackingId(TrackingId.of(trackingId))
                .customer(CustomerTestDataHelper.CUSTOMER)
                .airline(AirlineTestDataHelper.AIRLINE)
                .streetAddress(ADDRESS)
                .items(List.of(items))
                .build();
  }

  public void initialiseMocks() {
    initialiseMocks(ORDER_1_TRACKING_ID, ORDER_1);
    initialiseMocks(ORDER_2_TRACKING_ID, ORDER_2);
    initialiseMocks(ORDER_3_TRACKING_ID, ORDER_3);

    lenient().when(orderRepository.save(any(Order.class)))
             .thenAnswer(theInputtedOrder());
  }


  private void initialiseMocks(final UUID trackingId, final Order order) {
    lenient().when(orderRepository.findByTrackingId(TrackingId.of(trackingId)))
             .thenReturn(Optional.of(order));
  }

  private Answer<Order> theInputtedOrder() {
    return invocation -> invocation.getArgument(0);
  }
}
