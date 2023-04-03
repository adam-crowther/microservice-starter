package com.acroteq.food.ordering.system.domain.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


public class ValidationResult {

  private static final String FAILURE_MESSAGE_DELIMITER = ",";

  private final List<String> failureMessages;

  public static ValidationResult of(final List<String> failureMessages) {
    final ValidationResult validationResult = new ValidationResult();
    validationResult.addFailureMessages(failureMessages);
    return validationResult;
  }

  public static ValidationResult pass() {
    return new ValidationResult();
  }

  public static ValidationResult fail(final String failureMessage) {
    final ValidationResult validationResult = new ValidationResult();
    validationResult.addFailureMessage(failureMessage);
    return validationResult;
  }

  public static ValidationResult fail(final String failureMessage, final Object... parameters) {
    final ValidationResult validationResult = new ValidationResult();
    validationResult.addFailureMessage(failureMessage, parameters);
    return validationResult;
  }

  private ValidationResult() {
    failureMessages = new ArrayList<>();
  }

  public List<String> getFailureMessages() {
    return unmodifiableList(failureMessages);
  }

  public String getLogOutput() {
    return String.join(FAILURE_MESSAGE_DELIMITER, failureMessages);
  }

  public void addFailureMessage(final String failureMessage) {
    if (isNotBlank(failureMessage) && !failureMessages.contains(failureMessage)) {
      failureMessages.add(failureMessage);
    }
  }

  public void addFailureMessages(final List<String> failureMessages) {
    final List<String> filteredMessages =
        failureMessages.stream()
                       .filter(StringUtils::isNotBlank)
                       .filter(m -> !failureMessages.contains(m))
                       .distinct()
                       .toList();

    if (!filteredMessages.isEmpty()) {
      failureMessages.addAll(filteredMessages);
    }
  }

  public void addFailureMessage(final String failureMessage, final Object... parameters) {
    final String message = String.format(failureMessage, parameters);
    addFailureMessage(message);
  }

  public boolean isPass() {
    return failureMessages.isEmpty();
  }

  public void add(final ValidationResult other) {
    final List<String> filteredMessages =
        other.getFailureMessages()
             .stream()
             .filter(StringUtils::isNotBlank)
             .filter(m -> !failureMessages.contains(m))
             .distinct()
             .toList();

    if (!filteredMessages.isEmpty()) {
      failureMessages.addAll(filteredMessages);
    }
  }
}
