package com.acroteq.domain.validation;

import static lombok.AccessLevel.PACKAGE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = PACKAGE)
public class ValidationResultBuilder {

  private final List<String> failureMessages = new ArrayList<>();

  public ValidationResultBuilder validationResult(@NonNull final ValidationResult other) {
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

  public ValidationResultBuilder failure(@NonNull final String failureMessage, final Object... parameters) {
    final String message = String.format(failureMessage, parameters);
    failure(message);
    return this;
  }

  public ValidationResultBuilder failure(@NonNull final String failureMessage) {
    if (isNotBlank(failureMessage) && !failureMessages.contains(failureMessage)) {
      failureMessages.add(failureMessage);
    }
    return this;
  }

  public ValidationResult build() {
    return ValidationResult.of(failureMessages);
  }
}
