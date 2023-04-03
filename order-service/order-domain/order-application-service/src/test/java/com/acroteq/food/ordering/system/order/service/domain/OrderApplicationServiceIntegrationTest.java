package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotActiveException;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import com.acroteq.food.ordering.system.order.service.domain.test.config.TestConfig;
import com.acroteq.food.ordering.system.order.service.domain.test.helper.CustomerTestDataHelper;
import com.acroteq.food.ordering.system.order.service.domain.test.helper.OrderTestDataHelper;
import com.acroteq.food.ordering.system.order.service.domain.test.helper.ProductTestDataHelper;
import com.acroteq.food.ordering.system.order.service.domain.test.helper.RestaurantTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderStatus.PENDING;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.CustomerTestDataHelper.CUSTOMER_ID;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_CITY;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_POSTAL_CODE;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_STREET;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.ProductTestDataHelper.PRODUCT_1_ID;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.RestaurantTestDataHelper.RESTAURANT;
import static com.acroteq.food.ordering.system.order.service.domain.test.helper.RestaurantTestDataHelper.RESTAURANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { TestOrderApplication.class, TestConfig.class })
public class OrderApplicationServiceIntegrationTest {

  @Autowired private RestaurantProductRepository restaurantProductRepository;

  @Autowired private CustomerTestDataHelper customerTestDataHelper;
  @Autowired private ProductTestDataHelper productTestDataHelper;
  @Autowired private RestaurantTestDataHelper restaurantTestDataHelper;
  @Autowired private OrderTestDataHelper orderTestDataHelper;

  @Autowired private OrderApplicationService orderApplicationService;

  @BeforeEach
  public void init() {
    customerTestDataHelper.initialiseMocks();
    orderTestDataHelper.initialiseMocks();
    restaurantTestDataHelper.initialiseMocks();
  }

  @Test
  public final void testCreateOrderOk() {
    // given:
    final CreateOrderCommandDto createOrderCommand = createCreateOrderCommand();
    // when:
    final CreateOrderResponseDto response = orderApplicationService.createOrder(createOrderCommand);

    // then:
    assertThat(response.getTrackingId(), is(notNullValue()));
    assertThat(response.getStatus(), is(PENDING));
    assertThat(response.getMessage(), is("Order created successfully"));
  }

  @Test
  public final void testCreateOrderWithPassiveRestaurantNok() {
    // given:
    final Restaurant restaurant = RESTAURANT.toBuilder()
                                            .active(false)
                                            .build();
    when(restaurantProductRepository.loadRestaurant(RestaurantId.of(RESTAURANT_ID))).thenReturn(Optional.of(restaurant));
    final CreateOrderCommandDto createOrderCommand = createCreateOrderCommand();

    // when:
    final RestaurantNotActiveException exception = assertThrows(RestaurantNotActiveException.class,
                                                                () -> orderApplicationService.createOrder(
                                                                    createOrderCommand));
    // then:
    assertThat(exception.getMessage(), startsWith("Restaurant "));
    assertThat(exception.getMessage(), containsString(RESTAURANT_ID.toString()));
    assertThat(exception.getMessage(), endsWith(" is currently not active"));
  }

  private static CreateOrderCommandDto createCreateOrderCommand() {
    final OrderAddressDto orderAddress = OrderAddressDto.builder()
                                                        .street(ADDRESS_STREET)
                                                        .postalCode(ADDRESS_POSTAL_CODE)
                                                        .city(ADDRESS_CITY)
                                                        .build();
    final OrderItemDto orderItem = OrderItemDto.builder()
                                               .productId(PRODUCT_1_ID)
                                               .quantity(OrderTestDataHelper.ORDER_1_ITEM_1_QUANTITY)
                                               .build();
    return CreateOrderCommandDto.builder()
                                .customerId(CUSTOMER_ID)
                                .restaurantId(RESTAURANT_ID)
                                .address(orderAddress)
                                .items(List.of(orderItem))
                                .build();
  }
}
