package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval.AirlineApprovalRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentCancelRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.AirlineRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.FlightRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class TestOrderApplication {

  @MockBean
  private PaymentRequestMessagePublisher orderCreatedPublisher;
  @MockBean
  private PaymentCancelRequestMessagePublisher orderCancelledPublisher;
  @MockBean
  private AirlineApprovalRequestMessagePublisher orderPaidPublisher;
  @MockBean
  private OrderRepository orderRepository;
  @MockBean
  private CustomerRepository customerRepository;
  @MockBean
  private AirlineRepository airlineRepository;
  @MockBean
  private FlightRepository flightRepository;

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }
}
