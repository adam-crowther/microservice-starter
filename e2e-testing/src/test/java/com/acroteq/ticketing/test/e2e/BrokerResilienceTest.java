package com.acroteq.ticketing.test.e2e;

import static com.acroteq.ticketing.test.e2e.api.AuthenticationHelper.authenticate;
import static com.acroteq.ticketing.test.e2e.api.DatabaseCheckerFactory.createDatabaseChecker;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.acroteq.infrastructure.data.access.counter.JdbcDatabaseChecker;
import com.acroteq.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.e2e.extension.KafkaClusterContainers;
import com.acroteq.ticketing.test.e2e.extension.KafkaClusterExtension;
import com.acroteq.ticketing.test.e2e.extension.TestContainersExtension;
import com.acroteq.ticketing.test.e2e.extension.TestDockerContainers;
import com.acroteq.ticketing.test.e2e.uploader.CustomerUploader;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
@SuppressWarnings("PMD.ExcessiveImports")
@ExtendWith({ KafkaClusterExtension.class, TestContainersExtension.class })
class BrokerResilienceTest {

  private static final long DELAY_IN_MILLISECONDS = 1000L;
  private static final int SHUTDOWN_THRESHOLD_SECONDS = 15;
  private static final int STARTUP_THRESHOLD_SECONDS = 30;
  private static final int TEST_DURATION_SECONDS = 60;
  private static final int POLL_INTERVAL_SECONDS = 10;
  private static final int SHUTDOWN_TIMEOUT_SECONDS = 90;

  private static JdbcDatabaseChecker databaseChecker;
  private static List<KafkaSslContainer> kafkaContainers;
  private static CustomerUploader uploader;

  @BeforeAll
  static void startUp(final KafkaClusterContainers kafkaClusterContainers, final TestDockerContainers containers) {

    final String bearerToken = authenticate(containers);
    kafkaContainers = kafkaClusterContainers.getKafkaContainers();
    uploader = new CustomerUploader(containers, bearerToken);

    databaseChecker = createDatabaseChecker(containers);
  }

  @AfterAll
  static void shutdown() {
    databaseChecker.close();
  }

  @BeforeEach
  void resetCount() {
    uploader.resetCustomerCount();
  }

  // Shut down one of a cluster of 3 brokers while the customer entity replication is running, and restart it a
  // couple of seconds later.  No events should be lost.
  @Test
  void testBrokerResilience() {
    // Generate one new customer record per second
    final ScheduledThreadPoolExecutor createCustomerThread = new ScheduledThreadPoolExecutor(1);
    createCustomerThread.scheduleWithFixedDelay(uploader::createNewCustomer, 0, DELAY_IN_MILLISECONDS, MILLISECONDS);

    // After 15 seconds, shut down the Kafka broker for 15 seconds
    final ScheduledThreadPoolExecutor shutdownKafkaThread = new ScheduledThreadPoolExecutor(1);
    shutdownKafkaThread.schedule(this::shutdownKafkaInstance, SHUTDOWN_THRESHOLD_SECONDS, SECONDS);

    // After 30 seconds, restart the Kafka broker
    final ScheduledThreadPoolExecutor startupKafkaThread = new ScheduledThreadPoolExecutor(1);
    startupKafkaThread.schedule(this::startupKafkaInstance, STARTUP_THRESHOLD_SECONDS, SECONDS);

    // Stop generating customers after 60 seconds
    final ScheduledThreadPoolExecutor killCreateCustomerThread = new ScheduledThreadPoolExecutor(1);
    killCreateCustomerThread.schedule(createCustomerThread::shutdown, TEST_DURATION_SECONDS, SECONDS);

    // Wait for the createCustomer thread to be shut down
    Awaitility.await()
              .pollInterval(POLL_INTERVAL_SECONDS, SECONDS)
              .atMost(SHUTDOWN_TIMEOUT_SECONDS, SECONDS)
              .until(createCustomerThread::isShutdown);

    // Check that the data was replicated correctly
    final int count = databaseChecker.inCustomerMdmGetCustomerCount();
    assertThat(count, is(equalTo(uploader.getCustomerCount())));

    databaseChecker.inOrderServiceWaitForCustomersCount(count);
    databaseChecker.inPaymentServiceWaitForCustomersCount(count);
  }

  private void shutdownKafkaInstance() {
    kafkaContainers.get(1)
                   .stop();
  }

  private void startupKafkaInstance() {
    kafkaContainers.get(1)
                   .start();
  }
}
