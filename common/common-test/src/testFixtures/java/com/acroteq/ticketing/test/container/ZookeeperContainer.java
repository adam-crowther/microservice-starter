package com.acroteq.ticketing.test.container;

import static com.acroteq.ticketing.test.container.KafkaSslContainer.KAFKA_IMAGE_NAME;
import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkName;

import com.acroteq.ticketing.test.extension.HostNameSetter;
import com.acroteq.ticketing.test.extension.OutputFrameLogger;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.utility.DockerImageName;

public class ZookeeperContainer extends GenericContainer<ZookeeperContainer> {

  private static final String CONTAINER_NAME = "Zookeeper";

  private static final String STARTER_SCRIPT = "/testcontainers_start.sh";
  private static final int ZOOKEEPER_PORT = 2181;

  private static final String SHEBANG = "#!/bin/bash";
  private static final String LINE_BREAK = "\n";

  @SuppressWarnings({ "OctalInteger", "PMD.AvoidUsingOctalValues" })
  private static final int FILE_MODE_0777 = 0777;

  public static ZookeeperContainer startZookeeper() {
    final ZookeeperContainer zookeeperContainer = new ZookeeperContainer();
    zookeeperContainer.start();
    return zookeeperContainer;
  }

  @SuppressWarnings("resource")
  public ZookeeperContainer() {
    super(DockerImageName.parse(KAFKA_IMAGE_NAME));

    final OutputFrameLogger logConsumer = new OutputFrameLogger(CONTAINER_NAME);
    final HostNameSetter hostNameSetter = new HostNameSetter(CONTAINER_NAME);

    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());
    withLogConsumer(logConsumer);
    withCreateContainerCmdModifier(hostNameSetter);

    withExposedPorts(ZOOKEEPER_PORT);

    withCreateContainerCmdModifier(cmd -> {
      cmd.withEntrypoint("sh");
    });
    withCommand("-c", "while [ ! -f " + STARTER_SCRIPT + " ]; do sleep 0.1; done; " + STARTER_SCRIPT);
  }

  public String getInternalConnectUrl() {
    final String hostName = getContainerInfo().getConfig()
                                              .getHostName();
    return String.format("%s:%d", hostName, ZOOKEEPER_PORT);
  }

  public String getMappedConnectUrl() {
    final int mappedPort = getMappedPort(ZOOKEEPER_PORT);
    return String.format("localhost:%d", mappedPort);
  }

  @Override
  protected void containerIsStarting(final InspectContainerResponse containerInfo) {
    super.containerIsStarting(containerInfo);

    final String commands = SHEBANG
                            + LINE_BREAK
                            + LINE_BREAK
                            + "echo 'clientPort=" + ZOOKEEPER_PORT + "' > zookeeper.properties"
                            + LINE_BREAK
                            + "echo 'dataDir=/var/lib/zookeeper/data' >> zookeeper.properties"
                            + LINE_BREAK
                            + "echo 'dataLogDir=/var/lib/zookeeper/log' >> zookeeper.properties"
                            + LINE_BREAK
                            + "zookeeper-server-start zookeeper.properties"
                            + LINE_BREAK;

    final Transferable file = Transferable.of(commands, FILE_MODE_0777);
    copyFileToContainer(file, STARTER_SCRIPT);
  }
}
