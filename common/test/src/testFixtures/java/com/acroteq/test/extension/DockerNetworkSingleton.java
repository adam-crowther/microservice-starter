package com.acroteq.test.extension;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.Network;

import java.util.concurrent.ThreadLocalRandom;

public final class DockerNetworkSingleton {

  private static final int MIN_SUFFIX = 10_000;
  private static final int MAX_SUFFIX = 99_999;

  // private static final String NETWORK_NAME = "app-tier" + getSuffix();
  private static final String NETWORK_NAME = "app-tier";
  // private static final Network NETWORK = Network.builder()
  //                                               .createNetworkCmdModifier(cmd -> cmd.withName(NETWORK_NAME))
  //                                               .build();

  @SuppressFBWarnings("PREDICTABLE_RANDOM")
  private static int getSuffix() {
    return ThreadLocalRandom.current()
                            .nextInt(MIN_SUFFIX, MAX_SUFFIX);
  }

  public static Network getNetworkInstance() {
    // return NETWORK;
    return new Network() {
      @Override
      public String getId() {
        return NETWORK_NAME; // use id of external network
      }

      @Override
      public void close() {
      }

      @Override
      public Statement apply(Statement base, Description description) {
        return null;
      }
    };
  }

  public static String getNetworkName() {
    return NETWORK_NAME;
  }

  private DockerNetworkSingleton() {
  }
}
