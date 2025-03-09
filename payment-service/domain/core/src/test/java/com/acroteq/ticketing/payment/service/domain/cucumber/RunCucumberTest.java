package com.acroteq.ticketing.payment.service.domain.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * This JUnit test is used to drive the cucumber tests from the JUnit engine, which allows us to collect coverage data.
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:build/reports/cucumber-output.html" },
                 features = "src/test/resources/payment-domain-core")
public class RunCucumberTest {}
