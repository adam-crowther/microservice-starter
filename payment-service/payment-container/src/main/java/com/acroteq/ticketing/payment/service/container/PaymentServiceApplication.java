package com.acroteq.ticketing.payment.service.container;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.acroteq.ticketing")
public class PaymentServiceApplication {

  public static void main(final String[] args) {
    // Without this the embedded Tomcat and other dependencies will log via JUL/JULI, which ends up on the console.
    // This bridge sends these logs to Slf4J.
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    SpringApplication.run(PaymentServiceApplication.class, args);
  }
}
