package com.acroteq.ticketing.precondition

import groovy.transform.CompileDynamic

@CompileDynamic
class TestException extends RuntimeException {

  TestException() {}

  TestException(final String message) {
    super(message)
  }

  TestException(final String message1, final String message2) {
    super(message1 + ',' + message2)
  }
}