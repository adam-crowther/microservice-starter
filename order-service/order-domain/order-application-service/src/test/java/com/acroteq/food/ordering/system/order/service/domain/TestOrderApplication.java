package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.RestaurantProductRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages =  "com.acroteq.food.ordering.system")
public class TestOrderApplication {

  @MockBean private OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
  @MockBean private OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;
  @MockBean private OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;
  @MockBean private OrderRepository orderRepository;
  @MockBean private CustomerRepository customerRepository;
  @MockBean private RestaurantProductRepository restaurantProductRepository;

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }
}
