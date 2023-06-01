package com.acroteq.ticketing.test.extension;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

@Slf4j
public class KafkaContainerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final KafkaSslContainer kafka = new KafkaSslContainer();

  @Override
  public void beforeAll(final ExtensionContext context) {
    kafka.start();

    final int mappedPlaintextBrokerPort = kafka.getMappedExposedPlaintextBrokerPort();

    final String bootstrapServers = String.format("localhost:%d", mappedPlaintextBrokerPort);
    System.setProperty("KAFKA_BOOTSTRAP_SERVERS", bootstrapServers);
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    kafka.stop();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return parameterContext.getParameter()
                           .getType()
                           .equals(KafkaSslContainer.class);
  }

  @Override
  public KafkaSslContainer resolveParameter(final ParameterContext parameterContext,
                                            final ExtensionContext extensionContext) {
    return kafka;
  }
}