package com.acroteq.ticketing.airline.service.domain.entity;

import static com.acroteq.precondition.Precondition.checkPrecondition;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineValidationException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.function.Predicate;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Flight extends PrimaryEntity<FlightId> {

  @NonNull
  private final String code;
  @NonNull
  private final CashValue price;
  private final boolean available;

  public void validate() {
    checkPrecondition(isNotBlank(code), AirlineValidationException::new, "code");
  }

  public static Predicate<Flight> hasCode(final String code) {
    return flight -> flight.code.equals(code);
  }
}
