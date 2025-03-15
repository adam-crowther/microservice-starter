package com.acroteq.ticketing.airline.service.presentation.exception.handler;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.acroteq.common.application.exception.handler.Problem;
import com.acroteq.domain.exception.DomainException;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * Spring ControllerAdvice class - exception handler for Spring MVC application.
 *
 * @see
 * <a href="https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Exception Handling in Spring MVC</a>
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(1)
public class AirlineServiceExceptionHandler {

  private final MessageSource messageSource;

  @ResponseBody
  @ExceptionHandler({ AirlineNotFoundException.class })
  @ResponseStatus(NOT_FOUND)
  public Problem handleException(final AirlineNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(NOT_FOUND, exception, locale);
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
