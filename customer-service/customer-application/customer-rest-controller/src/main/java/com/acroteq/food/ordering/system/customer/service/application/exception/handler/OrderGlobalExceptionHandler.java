package com.acroteq.food.ordering.system.customer.service.application.exception.handler;

import com.acroteq.food.ordering.system.common.application.exception.handler.GlobalExceptionHandler;
import com.acroteq.food.ordering.system.common.application.exception.handler.Problem;
import com.acroteq.food.ordering.system.customer.service.domain.exception.CustomerNotFoundException;
import com.acroteq.food.ordering.system.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

import static java.util.Arrays.asList;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

  private final MessageSource messageSource;

  @ResponseBody
  @ExceptionHandler({ CustomerNotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Problem handleException(final CustomerNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.NOT_FOUND, exception, locale);
  }

  private Problem createProblem(final HttpStatus httpStatus, final DomainException exception, final Locale locale) {
    final String code = exception.getCode();
    final String[] parameters = exception.getParameters();
    final String message = messageSource.getMessage(code, parameters, locale);
    return Problem.builder()
                  .code(httpStatus.value())
                  .reason(httpStatus.getReasonPhrase())
                  .i18nCode(code)
                  .i18nParameters(asList(parameters))
                  .message(message)
                  .build();
  }
}
