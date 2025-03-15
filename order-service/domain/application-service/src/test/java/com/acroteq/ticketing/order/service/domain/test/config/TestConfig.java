package com.acroteq.ticketing.order.service.domain.test.config;

import com.acroteq.ticketing.order.service.domain.OrderDomainService;
import com.acroteq.ticketing.order.service.domain.OrderDomainServiceImpl;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.AirlineApprovalRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentCancelRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.acroteq.ticketing.order.service.domain.test.helper")
public class TestConfig {

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }

  @Bean
  public PaymentRequestMessagePublisher orderCreatedPublisher() {
    return Mockito.mock(PaymentRequestMessagePublisher.class);
  }

  @Bean
  public PaymentCancelRequestMessagePublisher orderCancelledPublisher() {
    return Mockito.mock(PaymentCancelRequestMessagePublisher.class);
  }

  @Bean
  public AirlineApprovalRequestMessagePublisher orderPaidPublisher() {
    return Mockito.mock(AirlineApprovalRequestMessagePublisher.class);
  }

  @Bean
  public OrderRepository orderRepository() {
    return Mockito.mock(OrderRepository.class);
  }

  @Bean
  public CustomerRepository customerRepository() {
    return Mockito.mock(CustomerRepository.class);
  }

  @Bean
  public AirlineRepository airlineRepository() {
    return Mockito.mock(AirlineRepository.class);
  }

  @Bean
  public FlightRepository flightRepository() {
    return Mockito.mock(FlightRepository.class);
  }
}
