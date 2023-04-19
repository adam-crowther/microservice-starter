package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.OrderPaidAirlineRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineFlightRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class TestOrderApplication {

  @MockBean
  private OrderCreatedPaymentRequestMessagePublisher orderCreatedPublisher;
  @MockBean
  private OrderCancelledPaymentRequestMessagePublisher orderCancelledPublisher;
  @MockBean
  private OrderPaidAirlineRequestMessagePublisher orderPaidPublisher;
  @MockBean
  private OrderRepository orderRepository;
  @MockBean
  private CustomerRepository customerRepository;
  @MockBean
  private AirlineFlightRepository airlineFlightRepository;

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }
}
