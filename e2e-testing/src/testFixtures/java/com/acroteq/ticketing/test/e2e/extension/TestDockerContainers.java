package com.acroteq.ticketing.test.e2e.extension;

import static com.acroteq.ticketing.test.container.KeycloakIamContainer.startKeycloakContainer;
import static com.acroteq.ticketing.test.container.PostgreSqlContainer.startPostgreSqlContainer;
import static com.acroteq.ticketing.test.container.SchemaRegistryContainer.startSchemaRegistryContainer;
import static com.acroteq.ticketing.test.e2e.container.AirlineApprovalContainer.startAirlineApprovalContainer;
import static com.acroteq.ticketing.test.e2e.container.AirlineMdmContainer.startAirlineMdmContainer;
import static com.acroteq.ticketing.test.e2e.container.CustomerMdmContainer.startCustomerMdmContainer;
import static com.acroteq.ticketing.test.e2e.container.OrderServiceContainer.startOrderServiceContainer;
import static com.acroteq.ticketing.test.e2e.container.PaymentServiceContainer.startPaymentServiceContainer;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.KeycloakIamContainer;
import com.acroteq.ticketing.test.container.PostgreSqlContainer;
import com.acroteq.ticketing.test.container.SchemaRegistryContainer;
import com.acroteq.ticketing.test.e2e.container.AirlineApprovalContainer;
import com.acroteq.ticketing.test.e2e.container.AirlineMdmContainer;
import com.acroteq.ticketing.test.e2e.container.CustomerMdmContainer;
import com.acroteq.ticketing.test.e2e.container.OrderServiceContainer;
import com.acroteq.ticketing.test.e2e.container.PaymentServiceContainer;
import lombok.Getter;

import java.util.List;

@Getter
public class TestDockerContainers {

  private PostgreSqlContainer postgreSqlContainer;
  private KeycloakIamContainer keycloakContainer;
  private SchemaRegistryContainer schemaRegistryContainer;
  private CustomerMdmContainer customerMdmContainer;
  private AirlineMdmContainer airlineMdmContainer;
  private AirlineApprovalContainer airlineApprovalContainer;
  private OrderServiceContainer orderServiceContainer;
  private PaymentServiceContainer paymentServiceContainer;

  void start(final List<KafkaSslContainer> kafkaContainers) {
    postgreSqlContainer = startPostgreSqlContainer();
    keycloakContainer = startKeycloakContainer();
    schemaRegistryContainer = startSchemaRegistryContainer(kafkaContainers);
    customerMdmContainer = startCustomerMdmContainer(postgreSqlContainer,
                                                     kafkaContainers,
                                                     schemaRegistryContainer,
                                                     keycloakContainer);
    airlineMdmContainer = startAirlineMdmContainer(postgreSqlContainer,
                                                   kafkaContainers,
                                                   schemaRegistryContainer,
                                                   keycloakContainer);
    airlineApprovalContainer = startAirlineApprovalContainer(postgreSqlContainer,
                                                             kafkaContainers,
                                                             schemaRegistryContainer,
                                                             keycloakContainer);
    orderServiceContainer = startOrderServiceContainer(postgreSqlContainer,
                                                       kafkaContainers,
                                                       schemaRegistryContainer,
                                                       keycloakContainer);
    paymentServiceContainer = startPaymentServiceContainer(postgreSqlContainer,
                                                           kafkaContainers,
                                                           schemaRegistryContainer,
                                                           keycloakContainer);
  }

  void stop() {
    postgreSqlContainer.stop();
    keycloakContainer.stop();
    schemaRegistryContainer.stop();
    customerMdmContainer.stop();
    airlineMdmContainer.stop();
    airlineApprovalContainer.stop();
    orderServiceContainer.stop();
    paymentServiceContainer.stop();
  }

  public String getPostgreSqlHost() {
    return postgreSqlContainer.getHost();
  }

  public Integer getPostgreSqlPort() {
    return postgreSqlContainer.getFirstMappedPort();
  }

  public String getKeycloakAuthServerUrl() {
    return keycloakContainer.getAuthServerUrl();
  }

  public Integer getCustomerMdmPort() {
    return customerMdmContainer.getFirstMappedPort();
  }

  public Integer getAirlineMdmPort() {
    return airlineMdmContainer.getFirstMappedPort();
  }

  public Integer getOrderServicePort() {
    return orderServiceContainer.getFirstMappedPort();
  }
}
