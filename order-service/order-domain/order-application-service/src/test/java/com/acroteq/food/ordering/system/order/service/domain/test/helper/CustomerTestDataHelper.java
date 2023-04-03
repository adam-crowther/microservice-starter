package com.acroteq.food.ordering.system.order.service.domain.test.helper;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.lenient;

@TestComponent
public class CustomerTestDataHelper {

  public static final UUID CUSTOMER_ID = UUID.fromString("8dbc60b9-5d5b-4fa3-8fc6-b728ebda094d");
  public static final Customer CUSTOMER = Customer.builder()
                                                  .id(CustomerId.of(CUSTOMER_ID))
                                                  .build();

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerTestDataHelper(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void initialiseMocks() {
    lenient().when(customerRepository.customerExists(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(true);
    lenient().when(customerRepository.findCustomer(CustomerId.of(CUSTOMER_ID)))
             .thenReturn(Optional.of(CUSTOMER));
  }
}
