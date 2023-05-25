package com.acroteq.ticketing.test.extension;

import static org.testcontainers.containers.output.OutputFrame.OutputType.STDERR;
import static org.testcontainers.containers.output.OutputFrame.OutputType.STDOUT;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class KafkaContainerExtension implements BeforeAllCallback, AfterAllCallback {

  private static final String KAFKA_DOCKER_IMAGE = "confluentinc/cp-kafka:7.3.2";

  @SuppressWarnings({ "resource", "PMD.CloseResource" })
  @Override
  public void beforeAll(final ExtensionContext context) {

    final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse(KAFKA_DOCKER_IMAGE)).withKraft()
                                                                                              .withLogConsumer(this::logOutputFrame);

    kafka.start();
    final String bootstrapServers = String.format(kafka.getBootstrapServers());
    System.setProperty("spring.kafka.bootstrap-servers", bootstrapServers);
    System.setProperty("spring.kafka.schema.producer.properties.registry.url", "");
    System.setProperty("spring.kafka.schema.consumer.properties.registry.url", "");
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    // do nothing, Testcontainers handles container shutdown
  }

  private void logOutputFrame(final OutputFrame outputFrame) {
    if (outputFrame.getType() == STDOUT) {
      log.info(outputFrame.getUtf8String());
    } else if (outputFrame.getType() == STDERR) {
      log.error(outputFrame.getUtf8String());
    }
  }
}