package com.acroteq.common.application.exception.handler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
@Order
public class GlobalExceptionHandler {

  private static final String I18N_CODE_CONSTRAINT_VIOLATION = "problem.constraint.violation";
  private static final String I18N_CODE_VALIDATION_FAILED = "problem.validation.failed";
  private static final String I18N_CODE_INTERNAL_ERROR = "problem.internal.error";

  private static final String MESSAGE_UNEXPECTED_ERROR = "Unexpected Error";
  private static final String MESSAGE_VALIDATION_EXCEPTION = "Validation Exception";

  @ResponseBody
  @ExceptionHandler({ ConstraintViolationException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final ConstraintViolationException exception) {
    log.error(exception.getMessage(), exception);
    final String violationMessages = extractViolationMessages(exception);
    final List<String> violationFields = extractViolationFields(exception);
    return Problem.builder()
                  .code(BAD_REQUEST.value())
                  .reason(BAD_REQUEST.getReasonPhrase())
                  .i18nCode(I18N_CODE_CONSTRAINT_VIOLATION)
                  .i18nParameters(violationFields)
                  .message(violationMessages)
                  .build();
  }

  @ResponseBody
  @ExceptionHandler({ ValidationException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final ValidationException exception) {
    log.error(exception.getMessage(), exception);
    return Problem.builder()
                  .code(BAD_REQUEST.value())
                  .reason(BAD_REQUEST.getReasonPhrase())
                  .message(MESSAGE_VALIDATION_EXCEPTION + ": " + exception.getMessage())
                  .i18nCode(I18N_CODE_VALIDATION_FAILED)
                  .build();
  }

  @ResponseBody
  @ExceptionHandler
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Problem handleException(final Exception exception) {
    log.error(exception.getMessage(), exception);
    return Problem.builder()
                  .code(INTERNAL_SERVER_ERROR.value())
                  .reason(INTERNAL_SERVER_ERROR.getReasonPhrase())
                  .message(MESSAGE_UNEXPECTED_ERROR)
                  .i18nCode(I18N_CODE_INTERNAL_ERROR)
                  .build();
  }

  private String extractViolationMessages(final ConstraintViolationException exception) {
    return exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(joining(", "));
  }

  private List<String> extractViolationFields(final ConstraintViolationException exception) {
    return exception.getConstraintViolations()
                    .stream()
                    .map(this::extractViolationField)
                    .toList();
  }

  private String extractViolationField(final ConstraintViolation<?> violation) {
    return String.format("'%s': '%s'", violation.getPropertyPath(), violation.getInvalidValue());
  }
}
