package com.acroteq.ticketing.domain.exception;

public class TestDomainException extends DomainException {

  private static final String I18N_CODE = "problem.test.domain.exception";
  private static final String MESSAGE = "Test Domain Exception %s";

  private final String testParameter;

  public TestDomainException(final String testParameter) {
    super(String.format(MESSAGE, testParameter));
    this.testParameter = testParameter;
  }

  public TestDomainException(final String testParameter, final Throwable cause) {
    super(String.format(MESSAGE, testParameter), cause);
    this.testParameter = testParameter;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ testParameter };
  }

}
