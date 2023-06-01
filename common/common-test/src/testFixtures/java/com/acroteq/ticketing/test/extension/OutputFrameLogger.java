package com.acroteq.ticketing.test.extension;

import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static org.testcontainers.containers.output.OutputFrame.OutputType.STDERR;
import static org.testcontainers.containers.output.OutputFrame.OutputType.STDOUT;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.testcontainers.containers.output.OutputFrame;

import java.util.function.Consumer;

@Slf4j
public class OutputFrameLogger implements Consumer<OutputFrame> {

  private final Marker marker;
  private final String logName;

  public OutputFrameLogger(final String logName) {
    final String markerName = convertToUpperCaseUnderscore(logName);

    this.marker = MarkerFactory.getMarker(markerName);
    this.logName = logName;
  }

  private String convertToUpperCaseUnderscore(final String logName) {
    return UPPER_CAMEL.to(UPPER_UNDERSCORE, logName);
  }

  @Override
  public void accept(final OutputFrame outputFrame) {
    if (outputFrame.getType() == STDOUT) {
      log.info(marker, "{}: {}", logName, outputFrame.getUtf8String());
    } else if (outputFrame.getType() == STDERR) {
      log.error(marker, "{}: {}", logName, outputFrame.getUtf8String());
    }
  }
}
