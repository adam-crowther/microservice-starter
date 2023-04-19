package com.acroteq.ticketing.order.service.domain.test.helper;

import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_CITY;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_POSTAL_CODE;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_STREET;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ORDER_1_ITEM_1_QUANTITY;
import static org.mockito.Mockito.lenient;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class CustomerTestDataHelper {

  public static final Long CUSTOMER_ID = IdTestDataHelper.nextId();
  public static final Customer CUSTOMER = Customer.builder()
                                                  .id(CustomerId.of(CUSTOMER_ID))
                                                  .build();

  private final CustomerRepository customerRepository;

  public static CreateOrderCommandDto createCreateOrderCommandDto() {
    final OrderAddressDto orderAddress = OrderAddressDto.builder()
                                                        .street(ADDRESS_STREET)
                                                        .postalCode(ADDRESS_POSTAL_CODE)
                                                        .city(ADDRESS_CITY)
                                                        .build();
    final OrderItemDto orderItem = OrderItemDto.builder()
                                               .flightId(FlightTestDataHelper.FLIGHT_1_ID)
                                               .quantity(ORDER_1_ITEM_1_QUANTITY)
                                               .build();
    return CreateOrderCommandDto.builder()
                                .customerId(CUSTOMER_ID)
                                .airlineId(AirlineTestDataHelper.AIRLINE_ID)
                                .address(orderAddress)
                                .items(List.of(orderItem))
                                .build();
  }

  void initialiseMocks() {
    lenient().when(customerRepository.customerExists(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(true);
    lenient().when(customerRepository.findCustomer(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(Optional.of(CUSTOMER));
  }
}
