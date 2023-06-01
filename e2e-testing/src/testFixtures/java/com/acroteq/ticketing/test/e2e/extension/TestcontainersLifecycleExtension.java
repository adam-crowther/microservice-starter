package com.acroteq.ticketing.test.e2e.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TestcontainersLifecycleExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private final TestDockerContainers testDockerContainers = new TestDockerContainers();

  @Override
  public void beforeAll(final ExtensionContext context) {
    testDockerContainers.start();
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
