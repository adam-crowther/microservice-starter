package com.acroteq.food.ordering.system.order.service.application.exception.handler;

import com.acroteq.food.ordering.system.common.application.exception.handler.GlobalExceptionHandler;
import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.order.service.application.model.Problem;
import com.acroteq.food.ordering.system.order.service.domain.exception.CustomerNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.InvalidOrderPrestateException;
import com.acroteq.food.ordering.system.order.service.domain.exception.InvalidPriceException;
import com.acroteq.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNameMismatchException;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.ProductPriceMismatchException;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotActiveException;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotFoundException;
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

  @ResponseBody
  @ExceptionHandler({ OrderNotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Problem handleException(final OrderNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.NOT_FOUND, exception, locale);

  }

  @ResponseBody
  @ExceptionHandler({ RestaurantNotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Problem handleException(final RestaurantNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.NOT_FOUND, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ InvalidOrderPrestateException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Problem handleException(final InvalidOrderPrestateException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ InvalidPriceException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Problem handleException(final InvalidPriceException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ ProductNameMismatchException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Problem handleException(final ProductNameMismatchException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ ProductNotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Problem handleException(final ProductNotFoundException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.NOT_FOUND, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ ProductPriceMismatchException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Problem handleException(final ProductPriceMismatchException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.BAD_REQUEST, exception, locale);
  }

  @ResponseBody
  @ExceptionHandler({ RestaurantNotActiveException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Problem handleException(final RestaurantNotActiveException exception, final Locale locale) {
    log.error(exception.getMessage(), exception);
    return createProblem(HttpStatus.BAD_REQUEST, exception, locale);
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
