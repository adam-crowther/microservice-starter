package com.acroteq.ticketing.test.e2e.extension;

import static com.acroteq.ticketing.test.extension.KafkaContainerExtension.KAFKA_CONTAINERS;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class KafkaClusterExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final KafkaClusterContainers kafkaClusterContainers = new KafkaClusterContainers();

  @Override
  public void beforeAll(final ExtensionContext context) {
    kafkaClusterContainers.start();

    final Namespace namespace = Namespace.create(KafkaSslContainer.class, context.getRequiredTestClass());
    context.getStore(namespace)
           .put(KAFKA_CONTAINERS, kafkaClusterContainers.getKafkaContainers());
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    kafkaClusterContainers.stop();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return parameterContext.getParameter()
                           .getType()
                           .equals(KafkaClusterContainers.class);
  }

  @Override
  public KafkaClusterContainers resolveParameter(final ParameterContext parameterContext,
                                                 final ExtensionContext extensionContext) {
    return kafkaClusterContainers;
  }
}
