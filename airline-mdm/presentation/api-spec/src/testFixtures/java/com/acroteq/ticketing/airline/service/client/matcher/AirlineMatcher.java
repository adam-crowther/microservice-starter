package com.acroteq.ticketing.airline.service.client.matcher;

import com.acroteq.ticketing.airline.service.client.model.Airline;
import com.acroteq.ticketing.airline.service.client.model.CreateAirline;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

@RequiredArgsConstructor
public class AirlineMatcher extends TypeSafeMatcher<Airline> {

  private final CreateAirline request;

  public static AirlineMatcher matches(final CreateAirline request) {
    return new AirlineMatcher(request);
  }

  @Override
  protected boolean matchesSafely(final Airline airline) {

    return Objects.equals(airline.getName(), request.getName()) && Objects.equals(airline.getActive(),
                                                                                  request.getActive());
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
  }
}
