package com.acroteq.ticketing.order.service.presentation.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.acroteq.domain.exception.DomainException;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotActiveException;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotFoundException;
import com.acroteq.ticketing.order.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.order.service.domain.exception.FlightNotFoundException;
import com.acroteq.ticketing.order.service.domain.exception.FlightNumberMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.FlightPriceMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.InvalidOrderPrestateException;
import com.acroteq.ticketing.order.service.domain.exception.InvalidPriceException;
import com.acroteq.ticketing.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.order.service.presentation.model.Problem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
@Order(1)
public class OrderGlobalExceptionHandler {

  private final MessageSource messageSource;

  @ResponseBody
  @ExceptionHandler({ CustomerNotFoundException.class })
  @ResponseStatus(NOT_FOUND)
  public Problem handleException(final CustomerNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(NOT_FOUND, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ OrderNotFoundException.class })
  @ResponseStatus(NOT_FOUND)
  public Problem handleException(final OrderNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(NOT_FOUND, exception, locale);

  }

  @ResponseBody
  @ExceptionHandler({ AirlineNotFoundException.class })
  @ResponseStatus(NOT_FOUND)
  public Problem handleException(final AirlineNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(NOT_FOUND, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ InvalidOrderPrestateException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final InvalidOrderPrestateException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ InvalidPriceException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final InvalidPriceException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ FlightNumberMismatchException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final FlightNumberMismatchException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ FlightNotFoundException.class })
  @ResponseStatus(NOT_FOUND)
  public Problem handleException(final FlightNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(NOT_FOUND, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ FlightPriceMismatchException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final FlightPriceMismatchException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ AirlineNotActiveException.class })
  @ResponseStatus(BAD_REQUEST)
  public Problem handleException(final AirlineNotActiveException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(BAD_REQUEST, exception, locale);
  }

  private Problem createProblem(final HttpStatus httpStatus, final DomainException exception, final Locale locale) {
    final String code = exception.getCode();
    final String[] parameters = exception.getParameters();
    final String message = messageSource.getMessage(code, parameters, locale);
    return Problem.builder()
                  .code(httpStatus.value())
                  .reason(httpStatus.getReasonPhrase())
                  .i18nCode(code)
                  .i18nParameters(List.of(parameters))
                  .message(message)
                  .build();
  }
}
