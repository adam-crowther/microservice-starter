package com.acroteq.ticketing.domain.validation;

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.APPROVED;
import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public final class ValidationResult {

  private static final String FAILURE_MESSAGE_DELIMITER = ",";

  private final ImmutableList<String> failureMessages;

  public static ValidationResultBuilder builder() {
    return new ValidationResultBuilder();
  }

  public static ValidationResult combine(final ValidationResult left, final ValidationResult right) {
    return ValidationResult.builder()
                           .addValidationResult(left)
                           .addValidationResult(right)
                           .build();
  }

  public static ValidationResult of(final List<String> failureMessages) {
    return new ValidationResult(failureMessages);
  }

  public static ValidationResult pass() {
    return new ValidationResult();
  }

  public static ValidationResult fail(final String failureMessage) {
    return new ValidationResult(failureMessage);
  }

  public static ValidationResult fail(final String failureMessage, final Object... parameters) {
    final String message = String.format(failureMessage, parameters);
    return new ValidationResult(message);
  }

  private ValidationResult() {
    this.failureMessages = ImmutableList.of();
  }

  private ValidationResult(final String failureMessage) {
    this.failureMessages = ImmutableList.of(failureMessage);
  }

  private ValidationResult(final List<String> failureMessages) {
    this.failureMessages = ImmutableList.<String>builder()
                                        .addAll(failureMessages)
                                        .build();
  }

  public List<String> getFailures() {
    return failureMessages;
  }

  public String getLogOutput() {
    return String.join(FAILURE_MESSAGE_DELIMITER, failureMessages);
  }

  public boolean isPass() {
    return failureMessages.isEmpty();
  }

  public OrderApprovalStatus getApprovalStatus() {
    return failureMessages.isEmpty() ? APPROVED : REJECTED;
  }

  public static class ValidationResultBuilder {

    private final List<String> failureMessages = new ArrayList<>();

    public ValidationResultBuilder addValidationResult(final ValidationResult other) {
      final List<String> filteredMessages = other.getFailures()
                                                 .stream()
                                                 .filter(StringUtils::isNotBlank)
                                                 .filter(message -> !failureMessages.contains(message))
                                                 .distinct()
                                                 .toList();

      if (!filteredMessages.isEmpty()) {
        failureMessages.addAll(filteredMessages);
      }

      return this;
    }

    public ValidationResultBuilder addFailure(final String failureMessage, final Object... parameters) {
      final String message = String.format(failureMessage, parameters);
      addFailure(message);
      return this;
    }

    public ValidationResultBuilder addFailure(final String failureMessage) {
      if (isNotBlank(failureMessage) && !failureMessages.contains(failureMessage)) {
        failureMessages.add(failureMessage);
      }
      return this;
    }

    public ValidationResult build() {
      return ValidationResult.of(failureMessages);
    }
  }
}
