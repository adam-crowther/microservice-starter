package com.acroteq.ticketing.test.e2e;

import static com.acroteq.ticketing.test.e2e.api.AuthenticationHelper.authenticate;
import static com.acroteq.ticketing.test.e2e.api.ClientApiFactory.createCustomersApi;
import static com.acroteq.ticketing.test.e2e.api.DatabaseCheckerFactory.createDatabaseChecker;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.acroteq.ticketing.customer.service.client.api.CustomersApi;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerResponse;
import com.acroteq.ticketing.infrastructure.data.access.counter.JdbcDatabaseChecker;
import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.e2e.data.TestDataGenerator;
import com.acroteq.ticketing.test.e2e.extension.KafkaClusterContainers;
import com.acroteq.ticketing.test.e2e.extension.KafkaClusterExtension;
import com.acroteq.ticketing.test.e2e.extension.TestContainersExtension;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@SuppressWarnings("PMD.ExcessiveImports")
@ExtendWith({ KafkaClusterExtension.class, TestContainersExtension.class })
class BrokerResilienceTest {

  private static final long DELAY_IN_MILLISECONDS = 1000L;

  private static CustomersApi customersApi;

  private static JdbcDatabaseChecker databaseChecker;
  private static List<KafkaSslContainer> kafkaContainers;
  private final TestDataGenerator testDataGenerator = new TestDataGenerator();

  @BeforeAll
  static void startUp(final KafkaClusterContainers kafkaClusterContainers, final TestDockerContainers testContainers) {

    final String bearerToken = authenticate(testContainers);
    customersApi = createCustomersApi(testContainers, bearerToken);
    databaseChecker = createDatabaseChecker(testContainers);

    kafkaContainers = kafkaClusterContainers.getKafkaContainers();
  }

  @AfterAll
  static void shutdown() {
    databaseChecker.close();
  }


  // Shut down one of a cluster of 3 brokers while the customer entity replication is running, and restart it a
  // couple of seconds later.  No events should be lost.
  @Test
  void testBrokerResilience() {
    // Generate one new customer record per second
    final AtomicInteger customerCount = new AtomicInteger();
    final ScheduledThreadPoolExecutor createCustomerThread = new ScheduledThreadPoolExecutor(1);
    createCustomerThread.scheduleWithFixedDelay(createNewCustomer(customerCount),
                                                0,
                                                DELAY_IN_MILLISECONDS,
                                                MILLISECONDS);

    // After 15 seconds, shut down the Kafka broker for 10 seconds
    final ScheduledThreadPoolExecutor shutdownKafkaThread = new ScheduledThreadPoolExecutor(1);
    shutdownKafkaThread.schedule(shutdownKafkaInstance(1), 15, SECONDS);

    // After 30 seconds, restart the Kafka broker
    final ScheduledThreadPoolExecutor startupKafkaThread = new ScheduledThreadPoolExecutor(1);
    startupKafkaThread.schedule(startupKafkaInstance(1), 30, SECONDS);

    // Stop generating customers after 60 seconds
    final ScheduledThreadPoolExecutor killCreateCustomerThread = new ScheduledThreadPoolExecutor(1);
    killCreateCustomerThread.schedule(createCustomerThread::shutdown, 60, SECONDS);

    // Wait for the createCustomer thread to be shut down
    Awaitility.await()
              .pollInterval(10, SECONDS)
              .atMost(90, SECONDS)
              .until(createCustomerThread::isShutdown);

    // Check that the data was replicated correctly
    final int count = databaseChecker.inCustomerMdmGetCustomerCount();
    assertThat(count, is(equalTo(customerCount.get())));

    databaseChecker.inOrderServiceWaitForCustomersCount(count);
    databaseChecker.inPaymentServiceWaitForCustomersCount(count);
  }

  private Runnable createNewCustomer(final AtomicInteger customerCount) {
    return () -> {
      final int count = customerCount.get();
      final Double creditLimit = count * 1000.0;
      final CreateCustomerCommand createCustomerCommand = testDataGenerator.getCreateCustomerCommand(creditLimit);
      final Optional<CreateCustomerResponse> createCustomerResponse = customersApi.createCustomer(createCustomerCommand)
                                                                                  .blockOptional();
      createCustomerResponse.ifPresentOrElse(incrementCustomerCount(customerCount),
                                             () -> log.warn("Can't create customer"));
    };
  }

  private Consumer<CreateCustomerResponse> incrementCustomerCount(final AtomicInteger customerCount) {
    return response -> incrementCustomerCount(response, customerCount);
  }

  private void incrementCustomerCount(final CreateCustomerResponse response, final AtomicInteger customerCount) {
    log.info("Created customer id {}", response.getId());
    customerCount.incrementAndGet();
  }

  private Runnable shutdownKafkaInstance(final int brokerId) {
    return () -> kafkaContainers.get(brokerId)
                                .stop();
  }

  private Runnable startupKafkaInstance(final int brokerId) {
    return () -> kafkaContainers.get(brokerId)
                                .start();
  }
}
