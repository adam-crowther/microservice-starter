package com.acroteq.ticketing.test.e2e.extension;

import static com.acroteq.ticketing.test.extension.KafkaContainerExtension.KAFKA_CONTAINERS;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.List;

public class TestContainersExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final TestDockerContainers testDockerContainers = new TestDockerContainers();

  @SuppressWarnings("unchecked")
  @Override
  public void beforeAll(final ExtensionContext context) {
    final Namespace namespace = Namespace.create(KafkaSslContainer.class, context.getRequiredTestClass());
    final List<KafkaSslContainer> kafkaContainers = context.getStore(namespace)
                                                           .get(KAFKA_CONTAINERS, List.class);
    testDockerContainers.start(kafkaContainers);
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    testDockerContainers.stop();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return parameterContext.getParameter()
                           .getType()
                           .equals(TestDockerContainers.class);
  }

  @Override
  public TestDockerContainers resolveParameter(final ParameterContext parameterContext,
                                               final ExtensionContext extensionContext) {
    return testDockerContainers;
  }
}
