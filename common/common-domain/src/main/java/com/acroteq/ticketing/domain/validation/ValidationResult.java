package com.acroteq.ticketing.domain.validation;

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.APPROVED;
import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED;

import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.List;


public final class ValidationResult {

  private static final String FAILURE_MESSAGE_DELIMITER = ",";

  private final ImmutableList<String> failureMessages;

  public static ValidationResultBuilder builder() {
    return new ValidationResultBuilder();
  }

  public static ValidationResult combine(@NonNull final ValidationResult left, @NonNull final ValidationResult right) {
    return ValidationResult.builder()
                           .validationResult(left)
                           .validationResult(right)
                           .build();
  }

  public static ValidationResult of(@NonNull final List<String> failureMessages) {
    return new ValidationResult(failureMessages);
  }

  public static ValidationResult pass() {
    return new ValidationResult();
  }

  public static ValidationResult fail(@NonNull final String failureMessage) {
    return new ValidationResult(failureMessage);
  }

  public static ValidationResult fail(@NonNull final String failureMessage, final Object... parameters) {
    final String message = String.format(failureMessage, parameters);
    return new ValidationResult(message);
  }

  private ValidationResult() {
    this.failureMessages = ImmutableList.of();
  }

  private ValidationResult(final List<String> failureMessages) {
    this.failureMessages = ImmutableList.copyOf(failureMessages);
  }

  private ValidationResult(final String failureMessage) {
    this.failureMessages = ImmutableList.of(failureMessage);
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
}
