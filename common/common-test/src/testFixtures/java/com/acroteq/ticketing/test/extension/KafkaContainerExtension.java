package com.acroteq.ticketing.test.extension;

import static java.util.Collections.singletonList;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

@Slf4j
public class KafkaContainerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  public static final String KAFKA_CONTAINERS = "kafka-containers";

  private final KafkaSslContainer kafka = new KafkaSslContainer(1, 1);

  @Override
  public void beforeAll(final ExtensionContext context) {
    kafka.start();

    final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(context.getRequiredTestClass());
    context.getStore(namespace)
           .put(KAFKA_CONTAINERS, singletonList(kafka));
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