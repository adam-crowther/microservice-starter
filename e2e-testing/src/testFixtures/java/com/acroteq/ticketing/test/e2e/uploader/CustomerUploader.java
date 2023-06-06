package com.acroteq.ticketing.test.e2e.uploader;

import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createCustomersApi;

import com.acroteq.ticketing.customer.service.client.api.CustomersApi;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerResponse;
import com.acroteq.ticketing.test.e2e.data.TestDataGenerator;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CustomerUploader {

  private final AtomicInteger customerCount;
  private final TestDataGenerator testDataGenerator;

  private final CustomersApi customersApi;

  public CustomerUploader(final TestDockerContainers testContainers, final String bearerToken) {
    customersApi = createCustomersApi(testContainers, bearerToken);

    customerCount = new AtomicInteger();
    testDataGenerator = new TestDataGenerator();
  }

  public void createNewCustomer() {
    final int count = customerCount.get();
    final Double creditLimit = count * 1000.0;
    final CreateCustomerCommand createCustomerCommand = testDataGenerator.getCreateCustomerCommand(creditLimit);
    final Optional<CreateCustomerResponse> createCustomerResponse = customersApi.createCustomer(createCustomerCommand)
                                                                                .blockOptional();
    createCustomerResponse.ifPresentOrElse(this::incrementCustomerCount,
                                           () -> log.warn("Can't create customer"));
  }

  private void incrementCustomerCount(final CreateCustomerResponse response) {
    log.info("Created customer id {}", response.getId());
    customerCount.incrementAndGet();
  }

  public void resetCustomerCount() {
    customerCount.set(0);
  }

  public int getCustomerCount() {
    return customerCount.get();
  }
}
