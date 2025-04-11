package com.acroteq.ticketing.order.service.domain.test.helper;

import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_CITY;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_POSTAL_CODE;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ADDRESS_STREET;
import static com.acroteq.ticketing.order.service.domain.test.helper.OrderTestDataHelper.ORDER_1_ITEM_1_QUANTITY;
import static org.mockito.Mockito.lenient;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@TestComponent
public class CustomerTestDataHelper {

  public static final Long CUSTOMER_ID = IdTestDataHelper.nextId();
  public static final String CUSTOMER_FIRST_NAME = "customer-first-name";
  public static final String CUSTOMER_LAST_NAME = "customer-last-name";
  public static final String CUSTOMER_USER_NAME = "customer-user-name";

  public static final Customer CUSTOMER = Customer.builder()
                                                  .id(CustomerId.of(CUSTOMER_ID))
                                                  .version(0L)
                                                  .eventId(EventId.builder()
                                                                  .partition(0)
                                                                  .offset(0L)
                                                                  .build())
                                                  .firstName(CUSTOMER_FIRST_NAME)
                                                  .lastName(CUSTOMER_LAST_NAME)
                                                  .userName(CUSTOMER_USER_NAME)
                                                  .build();

  private final CustomerRepository customerRepository;

  public static Order createOrder(final Customer customer, final Airline airline, final Flight flight) {
    final StreetAddress address = StreetAddress.builder()
                                               .street(ADDRESS_STREET)
                                               .postalCode(ADDRESS_POSTAL_CODE)
                                               .city(ADDRESS_CITY)
                                               .build();
    final OrderItem orderItem = OrderItem.builder()
                                         .flight(flight)
                                         .quantity(ORDER_1_ITEM_1_QUANTITY)
                                         .build();
    return Order.builder()
                .customer(customer)
                .airline(airline)
                .streetAddress(address)
                .items(List.of(orderItem))
                .build();
  }

  void initialiseMocks() {
    lenient().when(customerRepository.existsById(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(true);
    lenient().when(customerRepository.findById(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(Optional.of(CUSTOMER));
  }
}
