package com.acroteq.ticketing.test.extension;

import com.github.dockerjava.api.command.CreateContainerCmd;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class HostNameSetter implements Consumer<CreateContainerCmd> {

  private static final int MIN_SUFFIX = 10_000;
  private static final int MAX_SUFFIX = 99_999;

  private final String containerName;
  private final int suffix;

  @SuppressFBWarnings("PREDICTABLE_RANDOM")
  public HostNameSetter(final String containerName) {
    this.containerName = adjustContainerName(containerName);
    this.suffix = ThreadLocalRandom.current()
                                   .nextInt(MIN_SUFFIX, MAX_SUFFIX);
  }

  @Override
  public void accept(final CreateContainerCmd command) {
    final String hostName = getHostName();
    command.withName(hostName)
           .withHostName(hostName);
  }

  public String getHostName() {
    return containerName.toLowerCase(Locale.getDefault()) + suffix;
  }

  private String adjustContainerName(final String name) {
    final String truncated = StringUtils.left(name, 8);
    return truncated.toLowerCase(Locale.getDefault());
  }
}
